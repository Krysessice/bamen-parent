package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import java.math.BigDecimal;

public  class SysAgentVo extends SysAgent{

    private long leve;
    private String path;
    private BigDecimal SumAmount;
    private Long money;
    private Double scaleMoney;
    private Double sums;
    private  BigDecimal Sumystemcost;

    public SysAgentVo(Builder builder) {
        super(builder);
    }

    public SysAgentVo(){
        super();
    }

    public long getLeve() {
        return leve;
    }

    public void setLeve(long leve) {
        this.leve = leve;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BigDecimal getSumAmount() {
        return SumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        SumAmount = sumAmount;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Double getScaleMoney() {
        return scaleMoney;
    }

    public void setScaleMoney(Double scaleMoney) {
        this.scaleMoney = scaleMoney;
    }

    public Double getSums() {
        return sums;
    }

    public void setSums(Double sums) {
        this.sums = sums;
    }

    public BigDecimal getSumystemcost() {
        return Sumystemcost;
    }
}
