package battleship.classes;

import battleship.Main;

public class DifficultyLevel {

    private String label;
    private String name;
    private CSVDictReader dictionary;
    
    public DifficultyLevel(String name) {
        this.setName(name);
        dictionary = Main.getDictionary();
        setLabel(dictionary.getLabelByName(name).get(Main.getInterfaceLanguage()));
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
      public String toString() {
          return getLabel();
      }
    
}
