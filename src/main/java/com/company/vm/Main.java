package com.company.vm;

import com.company.vm.command.Command;
import com.company.vm.command.CommandType;
import com.company.vm.core.Executer;
import com.company.vm.core.Program;
import com.company.vm.cpu.BCpu;
import com.company.vm.cpu.ICpu;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Virtual Machine with Program Class ===");

        // Создание программы новым способом - ИСПРАВЛЕННЫЙ ВАРИАНТ
        Program prog = new Program();
        prog.add(new Command("init 10 20"));
        prog.add(new Command("init 11 25"));
        prog.add(new Command("ld a 10"));
        prog.add(new Command("ld b 11"));
        prog.add(new Command("ld c 11"));
        prog.add(new Command("add"));
        prog.add(new Command("mv a d"));
        prog.add(new Command("add"));
        prog.add(new Command("print"));

        // Альтернативные способы добавления (если нужны):
        // prog.add(new Command(CommandType.INIT, "10", "20")); // через CommandType
        // prog.add(new Command("init", "10", "20")); // через строковый тип

        // Демонстрация итерации
        System.out.println("=== Iterating through program ===");
        for (Command c : prog) {
            System.out.println(c);
        }

        // Анализ программы
        System.out.println("\n=== Program Analysis ===");
        System.out.println("Total commands: " + prog.size());
        System.out.println("Most popular instruction: " + prog.mostPopularInstruction());
        System.out.println(prog.getMemoryAddressRange());

        System.out.println("\n=== Instructions by frequency ===");
        for (Map.Entry<CommandType, Long> entry : prog.getInstructionsByFrequency()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " times");
        }

        // Выполнение программы
        System.out.println("\n=== Program Execution ===");
        ICpu cpu = BCpu.build();
        Executer exec = new Executer(cpu);
        exec.runStepByStep(prog);

        // Дополнительный пример с разными способами добавления команд
        System.out.println("\n=== Additional Example ===");
        Program prog2 = new Program();
        prog2.add("init 0 100");
        prog2.add("init 1 50");
        prog2.add(new Command(CommandType.LD, "a", "0")); // через CommandType
        prog2.add(new Command(CommandType.LD, "b", "1")); // через CommandType
        prog2.add(new Command("sub")); // через строку
        prog2.add(new Command("print")); // через строку

        System.out.println("Program 2 analysis:");
        System.out.println("Most popular: " + prog2.mostPopularInstruction());
        System.out.println(prog2.getMemoryAddressRange());

        // Выполнение второй программы
        System.out.println("\n=== Program 2 Execution ===");
        ICpu cpu2 = BCpu.build();
        Executer exec2 = new Executer(cpu2);
        exec2.runStepByStep(prog2);
    }
}