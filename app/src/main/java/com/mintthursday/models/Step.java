package com.mintthursday.models;

public class Step {
    private String stepInstruction;

    public Step(String stepInstruction) {
        this.stepInstruction = stepInstruction;
    }

    public String getStepInstruction() {
        return stepInstruction;
    }

    public void setStepInstruction(String stepInstruction) {
        this.stepInstruction = stepInstruction;
    }

    @Override
    public String toString() {
        return "Step{" +
                "stepInstruction='" + stepInstruction + '\'' +
                '}';
    }
}
