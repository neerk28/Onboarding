package com.intuitcraft.onboarding.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PublicCredentials {

    private String email;
    private String phoneNumber;

    public PublicCredentials(PublicCredentialsBuilder publicCredentialsBuilder) {
        this.email = publicCredentialsBuilder.email;
        this.phoneNumber = publicCredentialsBuilder.phoneNumber;
    }

    public static class PublicCredentialsBuilder {
        private String email;
        private String phoneNumber;

        public static PublicCredentialsBuilder newInstance()
        {
            return new PublicCredentialsBuilder();
        }

        public PublicCredentialsBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public PublicCredentialsBuilder setPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PublicCredentials build(){
            return new PublicCredentials(this);
        }
    }
}
