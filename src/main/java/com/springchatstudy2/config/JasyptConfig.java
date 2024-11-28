package com.springchatstudy2.config;

import jakarta.annotation.PostConstruct;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class JasyptConfig {
    @Value("${jasypt.encryptor.password}")
    private String encryptKey;

    @PostConstruct
    public void validateEncryptKey(){
        if(!StringUtils.hasLength(encryptKey)){
            throw new IllegalArgumentException("The encryption key (jasypt.encryptor.password) must not be null or empty.");
        }
    }

    @Bean(name="jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(encryptKey);
        config.setPoolSize(1);
        config.setAlgorithm("PBEWITHMD5ANDES");
        config.setStringOutputType("base64");
        config.setKeyObtentionIterations("1000");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        encryptor.setConfig(config);
        return encryptor;
    }

}
