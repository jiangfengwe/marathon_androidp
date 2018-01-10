package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/14.
 */

public class PayBean {


    /**
     * state : true
     * message : 成功
     * orderSecretMessage : V3J8huFchSbP5BCq9ZX/j0yBWHdJEDFdHht4HmZLN7kd0SdK9UBe0HEk4OQij8w5oyNAEMoGQ2ezXfYd1ztRtidg5B1Li5aN0akMlyvyuUt717wufmmesFO6L57RBKs7cHss5kCCg/PqjV+cWVP3nSceGRwxCoFNKSPZxCGNvSgCMi+8ivnxtM+xMu2cweo52hV0qsDVXNobMJMIz6m+wKGaQcH9fn0aD+hkOQdLUUC7UArr0LpYqGK5iuwSC6TRqL9Z946aJTDEc5l1EJ/Cmf2Ee2bEwLld7sQ2HHsq29CE6zP4DawXPw8FRqHps7ZZlOtRUQ2NuoD8gfkfvl1IjHM4Sj1uiTlw+dCUb9Nc7QIzucuz3hQRrWXmn9hO6oAcj20LoUSVq1OR59V2RLtwvMO7P+jNmggn97LOZYxFNSwdSzAbwYp1pC5EN4a59QSdR6GViCxp0JuSfOkcNLgzLK0dzpbQJwOx+ES18/lwn2Q0HjuW5zi2BRI1N0l1nmpmYpeSjPgXvKxj6IJTupqDKwwLSLzsBluek+4Vq66oPTqyFMJyzQUscZnA4S5tWUSA5NH0i9RdeulRRs6+IfiNP8m7POlU1hvqW9Vajyf9/3N6AIMB1iEjVoZclLJMFyQuN4JygsPNVMU8OjhQUZlRc5BN21mFoTycxMWfF+KDtK9XkbJ53NijseVTpedIpbjHZcCw4FKseHIEeX59AhLx8hAgfL+aGB/E41zzTKbl6RaG0vwY3NRvBNoyYHFj+fR0DoKFB/hNiILEhMSJqijwwFrN1kKGsfXvgxT8ErdT+Jfr9VP7JGnIc6fHPU89ja7xXgI+T/1iAeKxRbkMjv7mvjpV+rO4c8KxcqQpw8rdbP6/O7R9j91+zH9BbP7ytTAKFMuH3+A80adkyixuawhi7lGv79+JyjkLcfv7U6VEzq/8aEYo5x1MDCd8UoUxxfwYnGtnMkiS6tYkroet44fCuIuu0drEsmyN88p59lOPLRGrzSB43wO3iqPMNvbsymRZOclQOHJmGoRCZlwQAHSG8l/mL6PF91b0a6Di2F5BEsN1jM1JadEQuCdOXVXQnWnqvpliKwKKQMEVZNWGSIQ6QNIhPYEBP8J03kw6lOGKw6LNeUmNX0GILdN64T8f0dDMv8bVN361DxHz7pdaKGOdB6zE8cDxa5No8mE+if7vC303jxh1CFWnZqeWyAQ/sX6b/jDMtHtZNYq2mBdgpODnhchnU7banczsCPcfrf5bypQVl1UrWVIPzyd0yVw0z36flJ+NH4vGksR4ekHiWLkEFzbtKMhH5gcattYEh8QSh/+BIM5pI4ic37iytw5TbTpX3OBHuvv4H0+euDev6/7eF7um5nHtQFDPNF4MSCijD5bV0R7UnqiL3SE1pkT0iwTPn8cGC5sPKD+1Hui+RM8tnNPhH0ER5AXgi6bK16grVzSckEnZ0ugps8V2P9nXf6jiYkU/g1BhgMLE0EgpfP24s8jJNs0+YrzXvG36tUfuTC6UCdFu6a9GyCRe/WP2JuTPHdLx68CR8Qm704PZRwIZMBjU55NJs8XMSUaUQyU/Ftm15yRmNzhpd5NBaG5tQEVK2MF7MhrIrVIyhaDJO+kCbfla9qBmZVIZLg+z49utFU001FrouYF7Q8yIy3FgPRqJcuSk97K6jjPOWYNJ6/vppxEdoz9PhCexMS2f0WOqJEku3zTB23w6BG0Nt88/Q51UwlGUP8q6okOudInNXSISNgGh6N7CpYECarsNYBviFephTMMWNPFmf3R2qhSq3JLbIrm+lpX+5RZTkJmGP6q4K3VsdhHDF3bgbulPnQTnDsk=
     */

    private boolean state;
    private String message;
    private String orderSecretMessage;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderSecretMessage() {
        return orderSecretMessage;
    }

    public void setOrderSecretMessage(String orderSecretMessage) {
        this.orderSecretMessage = orderSecretMessage;
    }
}
