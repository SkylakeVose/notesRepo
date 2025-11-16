package cn.piggy.spring6.bean;

import java.util.Arrays;

public class QianDaYe {

    private String[] aiHaos;

    // 多位女性朋友
    private Woman[] womans;

    public void setWomans(Woman[] womans) {
        this.womans = womans;
    }

    public void setAiHaos(String[] aiHaos) {
        this.aiHaos = aiHaos;
    }

    @Override
    public String toString() {
        return "QianDaYe{" +
                "aiHaos=" + Arrays.toString(aiHaos) +
                ", womans=" + Arrays.toString(womans) +
                '}';
    }

}
