package com.github.sulo.core.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sulo.core.common.jwt.domain.JwtUser;
import com.github.sulo.core.exception.BizException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author sorata 2020-11-26:16:14
 */
public abstract class JwtTool {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final JwtConfig JWT_CONFIG;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        JWT_CONFIG = new JwtConfig();
        JWT_CONFIG.setAesEncode(false);
        JWT_CONFIG.setAesKey(Design.generatorKey(128));
        JWT_CONFIG.setAlgorithm(SignatureAlgorithm.HS256);
        JWT_CONFIG.setExpireDate(Date.from(LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.ofHours(8))));
        JWT_CONFIG.setKey(Keys.secretKeyFor(JWT_CONFIG.algorithm));
    }


    public static String create(JwtUser user) {
        try {
            String value;
            value = OBJECT_MAPPER.writeValueAsString(user);
            if (JWT_CONFIG.aesEncode) {
                value = Design.encode(value, JWT_CONFIG.aesKey);
            }
            return Jwts.builder()
                    .setIssuer(JWT_CONFIG.Issuer)
                    .setAudience(value)
                    .setNotBefore(new Date())
                    .setExpiration(JWT_CONFIG.expireDate)
                    .signWith(JWT_CONFIG.key, JWT_CONFIG.algorithm)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BizException(500, "create jwt token exception");
    }


    public static <T extends JwtUser> T parse(String token, Class<T> tClass) {
        if (token == null || token.isEmpty()) {
            throw new BizException(500, "token must not be null");
        }
        try {
            final Jws<Claims> claimsJws = Jwts.parserBuilder().requireIssuer(JWT_CONFIG.Issuer).setAllowedClockSkewSeconds(60)
                    .setSigningKey(JWT_CONFIG.key).build().parseClaimsJws(token);
            String jsonValue = claimsJws.getBody().getAudience();
            if (JWT_CONFIG.aesEncode) {
                jsonValue = Design.decode(jsonValue, JWT_CONFIG.aesKey);
            }
            return OBJECT_MAPPER.readValue(jsonValue, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BizException(500, "parse jwt token exception");
    }

    public static JwtConfig getJwtConfig() {
        return JWT_CONFIG;
    }

    public static class JwtConfig {

        private String Issuer;
        private String aesKey;
        private boolean aesEncode;
        private Key key;
        private SignatureAlgorithm algorithm;
        private Date expireDate;


        public String getIssuer() {
            return Issuer;
        }

        public void setIssuer(String issuer) {
            Issuer = issuer;
        }

        public String getAesKey() {
            return aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public boolean isAesEncode() {
            return aesEncode;
        }

        public void setAesEncode(boolean aesEncode) {
            this.aesEncode = aesEncode;
        }

        public Key getKey() {
            return key;
        }

        public void setKey(Key key) {
            this.key = key;
        }

        public SignatureAlgorithm getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(SignatureAlgorithm algorithm) {
            this.algorithm = algorithm;
        }

        public Date getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(Date expireDate) {
            this.expireDate = expireDate;
        }
    }


}
