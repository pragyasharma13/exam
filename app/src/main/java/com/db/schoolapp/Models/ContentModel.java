package com.db.schoolapp.Models;

public class ContentModel {
    private String privacy_policy;
    private String refund_policy;
    private String terms_and_conditions;
    private String about;

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public String getRefund_policy() {
        return refund_policy;
    }

    public void setRefund_policy(String refund_policy) {
        this.refund_policy = refund_policy;
    }

    public String getTerms_and_conditions() {
        return terms_and_conditions;
    }

    public void setTerms_and_conditions(String terms_and_conditions) {
        this.terms_and_conditions = terms_and_conditions;
    }

    public String getAbout() {
        return about;
    }

    public String setAbout(String about) {
        this.about = about;
        return about;
    }
}
