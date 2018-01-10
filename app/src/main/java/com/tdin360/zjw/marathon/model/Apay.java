package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/16.
 */

public class Apay {

    /**
     * state : true
     * message : 成功
     * orderSecretMessage : V3J8huFchSbP5BCq9ZX/j0yBWHdJEDFdHht4HmZLN7kd0SdK9UBe0HEk4OQij8w5oyNAEMoGQ2ezXfYd1ztRtidg5B1Li5aN0akMlyvyuUt717wufmmesFO6L57RBKs7cHss5kCCg/PqjV+cWVP3nSceGRwxCoFNKSPZxCGNvSgCMi+8ivnxtM+xMu2cweo52hV0qsDVXNobMJMIz6m+wKGaQcH9fn0aD+hkOQdLUUC7UArr0LpYqGK5iuwSC6TRqL9Z946aJTDEc5l1EJ/Cmf2Ee2bEwLld7sQ2HHsq29CE6zP4DawXPw8FRqHps7ZZlOtRUQ2NuoD8gfkfvl1IjHM4Sj1uiTlw+dCUb9Nc7QIzucuz3hQRrWXmn9hO6oAcj20LoUSVq1OR59V2RLtwvEKrNClwEg3fSodPDkkPIaWQTwyP/P9AHsuqumwXt2S7hM4weJnnnnMnF2HzGL9JIa5ZYXK6VAjj35ppaZhrdXxQY2mY7NuiHykymhPOKfVTXWTnf6FIaaSbm5mNJIWxQLW9LU2MCYSmofYroXYZef+AtzDjKs3f1w6VOBUYVlFBBW/JthxEEMU0pn9asH53aUrkzHKEgVHqFejAjJcCPH9PDUAvqH8EfkDKhVTx8P7oNpHxcdNGsoY6WlcsvSGdz6XzVV2HSk5+e5gJ4yNbIYfmM9OqlzbNs5/4+CUnh79Abrkv8sWrSg7NVbIFO2kuquXsgkorBQfPm9kivXHCydd0fxsXmRDOCvGT7BnsiUq+v1ajL22EHn2hfaFdDjl3jEzK9Jh+LWBIHZXhYNn+/cnCY00Zz7x/q+m9P/qqfsaCrZpiaoL6VnVzeU9KuuHzECBgjjgBdUPq7rzCgukjN5/nYFaD77HrnnpWtn3ehiSoUuFETr9UmyMQlS2oyzlNWiPd8vsxOO1/yrxHKnQVX7ruEyH5BdLWL+E4DY2U3W70yKjCe69hYCqbhfVCceQlj+8zSUHaQBQhr2Ed1XiREjIhGgNZgQ65Bj6oyZBk4cBXBX0uYxN2RkKp2TSdFjJwsDekRSkmLjfYio0SRA7bWP5PNSQwcdt2ZS1UP/WDUGgtYj9qrTWdkynOIcNWN6MHJUuWMkC04lx5cJ+7ByY9nmS6KxIJZIlWpn1eNwgn+4SJr8AfUxkgOyUhsYb0+snJP3HkAPhlXbMMAJkPBpr5zIBm8ADtPZVnxzF8p79qQYJN+tKxWMIk6UXjup/2OUMCW1Ta1q5XOd/t6m+K9+GvhQlxh0/Xas6ZeHCd1YM8Ya4rtjKGhm6qt4e59/ZakYr3/RDKE7R+UHYzQ8Hvwk/aE/FbsQx3BNTmYwanwl3R2Wn0MvvNMDWFv2vQH0M/dD9lribpJQ/lfqJZ2iH6XLHMGXlfoyo7+UHioLDHs+7p7aER8vcts10XTwwZ/v6a1/oRZD3/Z5SXBwEFh9oL4QHZ0+P7GyrdtFZnPstWeh4ry/uPVlbohds+5MqArdPdMEuUtn9O5SkUIULhv6rFkL3PcT4r3k+ywOeWo7tgu5wv+4YLOjWYtYsp/zNvAFK6E1IEVZVQvcKf9g5qw5tPFUs+HXGdLRGybGTHiYl2Sl1gQuZZVi560skLpJXGnp4Ii3FOXb5dwfQRLdEiANB4a7ij0Wpxah4WCVAoCiVK0M3Yzl9lFZXiHxzA082DdQbfalbUCznGoHnnJysnFSWa7yGp2vVNpZiQETx18NtCpsg4A4S+jUQt5WkcbAYQ8bbMm9ibtaaeLJ0wcZPOhwtSih9mZsjgdrOhLLJirgOnOMrVbE7uS9GMv1YIpxbWh8d5T1wnbeRosAVEbfscqCUFAE+tMdAQrolbCj5s7yaxBovd9zY+
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
