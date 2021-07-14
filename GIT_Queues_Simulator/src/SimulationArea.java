public class SimulationArea {
    private StringBuilder simulationTextArea;

    public SimulationArea() {
        simulationTextArea = new StringBuilder();
    }

    public void addText(String s)
    {
        simulationTextArea.append(s);
    }

    public String getSimulationTextArea() {
        return simulationTextArea.toString();
    }

    public void emptyStringBuilder()
    {
        simulationTextArea.delete(0,simulationTextArea.length()-1);
    }
}
