package com.aom.sopaDePalabra;

import com.aom.sopaDePalabra.clases.SopaDePalabra;
import com.aom.sopaDePalabra.clases.Sopas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class consoleCommandLineRunner implements CommandLineRunner {


    @Autowired
    Sopas sopas;

    @Autowired
    SopaDePalabra sopaDePalabra;

    @Override
    public void run(String... args) throws Exception {

        //sopaDePalabra.obtenerPalabrasAleatoriasDelDicconario(5);
        //sopaDePalabra.colocarPalabrasEnLaSopa();

        //System.out.println(sopaDePalabra.generarDireccionAleatoria());

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        this.printMenu();
        choice = scanner.nextInt();
        while (choice != 7){
            switch (choice){
                case 1:
                    try {
                        System.out.println("Listas de sopas");

                        System.out.println();
                        List<SopaDePalabra> listSopas = sopas.getListSopas();

                        Integer num = 1;
                        for (SopaDePalabra sopa: listSopas ) {
                            System.out.println( num+"- "+sopa.getId());
                            num++;
                        }
                    }catch (Exception e){
                        System.out.println("\n");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Entra el número de la sopa que quiere encontrar palabras");
                    Integer numero_sopa = scanner.nextInt();

                    System.out.println("");
                    try{
                        SopaDePalabra sopa = this.sopas.getSopaByPosicion(numero_sopa-1);
                        this.sopas.mostrarSopaEnConsola(sopa);
                    }catch (Exception e){
                        System.out.println("Error al recuperar la sopa");
                    }
                    break;
                case 3:

                    try{
                        System.out.println("Indicar palabra encontrada: ");

                        System.out.println("Entra el número de la sopa que quiere vizualizar");
                        Integer num_sopa = scanner.nextInt();
                        SopaDePalabra sopaADefinirCordenadas = this.sopas.getSopaByPosicion(num_sopa-1);

                        System.out.println("");
                        String mensajeCoordenadaInicial = "Entre coordenada inicial (f-c): ";
                        System.out.print(mensajeCoordenadaInicial);
                        String conrdenada_inicial = scanner.next();
                        while (!this.sopaDePalabra.coordenadasValidas(sopaADefinirCordenadas, conrdenada_inicial)){
                            System.out.print("Cordenada inválida, intente de nuevo") ;

                            System.out.println();
                            System.out.print(mensajeCoordenadaInicial);
                            conrdenada_inicial = scanner.next();
                        }

                        String mensajeCoordenadaFinal = "Entre coordenada final (f-c): ";
                        System.out.print(mensajeCoordenadaFinal);
                        String conrdenada_final = scanner.next();
                        while (!this.sopaDePalabra.coordenadasValidas(sopaADefinirCordenadas, conrdenada_final)){
                            System.out.print("Cordenada inválida, intente de nuevo") ;

                            System.out.println();
                            System.out.print(mensajeCoordenadaFinal);
                            conrdenada_final = scanner.next();
                        }

                        String palabra = sopaDePalabra.indicarPalabraEncontrada(sopaADefinirCordenadas, conrdenada_inicial, conrdenada_final, false);
                        if(!palabra.equals("palabra no encontrada")){
                            palabra = sopaDePalabra.indicarPalabraEncontrada(sopaADefinirCordenadas, conrdenada_inicial, conrdenada_final, true);
                        }
                        System.out.println(palabra);
                    }catch (Exception e){
                        System.out.println("\n");
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Parametro invalido");
                    break;
            }
            this.printMenu();
            choice = scanner.nextInt();
        }
        System.out.println("Bye");
    }

    public void printMenu(){
        System.out.println("\n");
        System.out.println("Opciones");
        System.out.println();
        System.out.println("Por favor seleccione la opción: \n"
                    + "1- Ver listas de sopas \n"
                    + "2- Ver sopa de palabras \n"
                    + "3- Indicar cordenadas de palabra \n"
                    + "4- Salir \n");
    }

    public boolean isNumeric(String num){
        try {
            Integer.parseInt(num.toString());
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
