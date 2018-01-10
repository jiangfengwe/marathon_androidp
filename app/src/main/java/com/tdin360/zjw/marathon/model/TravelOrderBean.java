package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/14.
 */

public class TravelOrderBean {

    /**
     * state : true
     * message : 订单生成成功
     * orderSecretMessage : ZZdmAGz5cWwG9Y2j+C+PORyyyhydX8NJ8HdNRZEjhltlowCVsBnbiMQPj5NTSiw+dTACq7q1T18PJl2/Yx2Lr2G0SOr5Jk4cra7PXZ5MSVyHN8BIfgDrtqKzCfDolcqskgVsl3A+cBKvOfGv7B9m2FCuGeqlJi1gFhlaybnJCbHWJ+JUNPQQP/CaZv0hQ1wu7wznAa52yy3ay6GE4nzvBwJHKXUxO/9x/y4gDDyWmTaYUQdcVv2uWe0TqFYIwLFeL0eQdojl/4pexw5bbwT7ncE633HpmBrffZtIBbpN1Kgo1dgObAPFF51ICWkeYxraU2lUyKFPkivSLEFGV6godjngE8XNDWAS+iyEaqDkQCf4T2CRZsG75nQVWW4ELxSIHtksouvqY9pDeikDdtj+7QXUYL50GgtkVUV/hdTCxf5fDFSlBNIyYhc3XuXkoJsSq2/oZNv7LOQ0nBnd9rbYfKT4l8wYFxqfBRll8Vc9oT20FtrU6MYWgEhioRGBhnHn9IW1sUZSfNzFN/UqGY71XaA+77QbvchD8Ue3ASOL+wXI+PjPZsxS5iZmZkzC+7HFTjVUmqTGl//omtO5K4/HexCvyjkmXxy96c1hvenvzuEb76s4brwxVtXBs97rJriPo1UJmfejVdfsmfDkI+4093PwbCTO8OuHJ473ihke9+lyNNcfow0omtgjRcxWbbtmi5RT8vkEagPvrP3pvUK5R6+GDGrSOEgV1SXLHuErXbhVNowGNKMJOjZkfBKT8BXXEQBgJfzm6rlgjD/MKo8wrUp3x68i7l06gnuaZyJdSvfTZXZG51z38SD/GU/bnoZFQfSEEZuQKmQZE4QS3ClJOVk7ER9zY8IOp8qez+SXptnwe4ADi+wewNNTxUrNEZ5cRap4+f6CsWVXYGIxSDQovewNQBK0Z5NL9TbuT3UJBE2se/twZsF1T2sDeZ6QBKVLCzgmQ307D3Ccrhn6ZHH6xp5NJ51op1TmbrhMuW/ViRCnaNYsE3uv+8Z2dud8EF6AR4M/mHwY7izzUzDZ6LnApfni3aDx5lBywKWHcSbgWnQqEj8QeRsDcEfuRMw4bwhCdik+LuoscEEKVic+1e/tym8MIEN+oCa9K07YYP06YI3hzfzke4uZB+E+QVHZ+dZyvGByv8eckIPUFBtDoZoAu+0QFf2luNNYL+IuYdvzDP7QQpC6kazG2MUuLscNuJJNMSa//oyHYfbk5fV5io1w0yTRhwrs6wB3IfPpylTE3FNPPTfSitDs7490Et0FH3+iaMSh9MDfZ+El2MkH7xTMzivsGjdFSPZDqdemMpx0kpgfXX4lZI42AvFz6t8/FXJaBt3Ql/hj+EDXx84eGVZmvpthFwZZa4K3FVtV5bfhCdlChM3RXJiNg7kQPGxItNC9sGqalvn6W493sH2J+/rD9kER7lay57PzFE48E1pgF4/TPU3reIcv28odF6YI+gsCM+mB74YKeAkAMCd7ELsAmoYo1E5CGAIfnGyi4myzIqjSonw3b1pktwJP156HDeX5
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
