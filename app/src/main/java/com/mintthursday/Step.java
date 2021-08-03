package com.mintthursday;

public class Step {
    private String stepInstruction;

    public Step(String stepInstruction) {
        this.stepInstruction = stepInstruction;
    }

    public String getStepInstruction() {
        return stepInstruction;
    }

    @Override
    public String toString() {
        return "Step{" +
                "stepInstruction='" + stepInstruction + '\'' +
                '}';
    }
}
