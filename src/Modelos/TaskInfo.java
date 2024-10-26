/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

public class TaskInfo {

    private String characterName;
    private boolean[] tasks; // Array para armazenar os status das tarefas

    public TaskInfo(String characterName, boolean[] tasks) {
        this.characterName = characterName;
        this.tasks = tasks;
    }

    // Getters e setters
    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public boolean[] getTasks() {
        return tasks;
    }

    public void setTasks(boolean[] tasks) {
        this.tasks = tasks;
    }
}