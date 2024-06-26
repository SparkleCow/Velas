package com.sparklecow.velas.services.email;

public enum EmailTemplate {
    ACTIVATE_ACCOUNT("activate_account");
    public String template;
    EmailTemplate(String template) {
        this.template = template;
    }
}

