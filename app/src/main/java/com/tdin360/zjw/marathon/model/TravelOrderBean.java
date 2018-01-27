package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/14.
 */

public class TravelOrderBean {


    /**
     * state : true
     * message : 订单生成成功
     * orderSecretMessage : ZZdmAGz5cWwG9Y2j+C+PORyyyhydX8NJ8HdNRZEjhltlowCVsBnbiMQPj5NTSiw+dTACq7q1T18PJl2/Yx2Lr2G0SOr5Jk4cra7PXZ5MSVyHN8BIfgDrtqKzCfDolcqskgVsl3A+cBKvOfGv7B9m2NUGJlwYV4Rj2PernNog5+oHPjojy08t5BLxJ+dZWIgbI82cYJpleDblBoz6UQnJrmh/g9DOPTB2MBv/N1y9M/uUtsdGurBiUhy+EI/i3qmjB9eDqYDTDC3VIrfLy8fPp/UFt7OpH0aNLJ7q3GrM1xe3CIWfJsGe/kg3VxqEjYNgyUT+kiA+M93HADXphFkExsIU726YDJ+z0F6ELTBsSUUCGdoVfp2XggJfrBTLhibctQY+DU/m65zItt4TkVvNenp4HbssOBwu7ffkgWl/ThSKM0nthcQXuytoJ4q1tNTnAqkztPihdh9O7kvXmjtuDQr1Pgjd6cq6n4MhDBhXKbGNDrkBxQgnfxm/EILzMu0dvVOYnGbh6J3jIO+xnCaROGFKe//sSjTr9SfM93xA+W9M23ER3N/XBrFog9SUqCh/2lXdwCf8muvKroq12YulGqzQ0Dyv3QNua2moIhiGQQAdKgpnJwDnfhy4qGtSY7+Plqw3WuKN/417tkPaU4zbzwzYer4/FMRQ11OO7ZsppjXamc5de+yBiGzoSw22b8HvGykIFcQnZ2khcOZKeZD1zwlYJEn3jPUEyfWKWWIjDPz33JpRl8M52rqj1kr56C/wZEBr6XUIqrd6O9Wwxgu0wgIBxbOGeec8gBifEOVgqFTOBVdYuBxQlXNDADeU1ZgGvObOjMpEsgr0GZG4dzihtF+VQ2AOL1ckUpCeLAUmVkcaCRgeiJE3Thx0JqeggftwYZidvtNmOMkuNfaz5DM/m9zB4FTAsAG7T4GkjqkXOdgqWcDIekOtAdHRPHzx8Wg152zzIWTpAozufxyOhTne3aJT47TLfFOsso1KserWcOOiCoHSq3HlmlMDgkoOX4V2H2kJvPrq8v+UUjz6VAvDYFqxyJbSJnGpeigAc3Nml49rTThjPxEbjuaB/jBD03S3qVhcGDCkxMjYSfPF7Iv7sBpETz8PwOpbBi/uxIshFp++PPwJloiXlSZgEJvGo3MoAKvGZD1pvVVvi3lE+DBbxnGYxRgkXpIuaMo1poYFdsJCE+JkP9593atcoYUhEv6jUzamOb0xKwenPZYZhdNn/9Lgd/0CBhvDOEyMalWEh8LwTiS9XdpqF1rVxKo2Cl2YgDL+P45ZQqGTOXM6d08ATkjMN7sWHfTHZ12SUn3HfOI=
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
