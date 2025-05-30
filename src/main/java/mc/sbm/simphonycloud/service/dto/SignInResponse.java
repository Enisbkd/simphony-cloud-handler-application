package mc.sbm.simphonycloud.service.dto;

public class SignInResponse {

    private String nextOp;
    private boolean success;
    private String redirectUrl;

    public String getNextOp() {
        return nextOp;
    }

    public void setNextOp(String nextOp) {
        this.nextOp = nextOp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
