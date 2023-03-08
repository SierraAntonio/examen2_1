package examen2;

import java.security.NoSuchAlgorithmException;

import java.util.*;

public abstract class Persona {
    private final String nombre;
    private final HashMap<String, String> atributos;

    public Persona(String nombre) {
        this.nombre = nombre;
        this.atributos = new HashMap<>();
    }

    public void agregarAtributo(String atributo, String valor) {
        atributos.put(atributo, valor);
    }

    public boolean tieneAtributo(String atributo) {
        return atributos.containsKey(atributo);
    }

    public String getNombre() {
        return nombre;
    }

    public HashMap<String, String> getAtributos() {
        return atributos;
    }

    public abstract String[] obtenerAtributosPosibles();




    public static void main(String[] args) throws NoSuchAlgorithmException {
        Random random = new Random();
        ArrayList<Persona> personas = new ArrayList<>();
        int[] atributosUsados = new int[4];
        String[] nombresH = {"Juan", "Carlos",  "Pedro", "Diego",  "Miguel"};
        String[] nombresM = { "Ana", "Maria", "Luisa", "Sofia",  "Isabel"};
        int npersona = 0;
        int x=0;
        int y=0;


        int pos;
        Stack < Integer > nNombres = new Stack< Integer >();
        for (int i = 0; i < 10 ; i++) {
            pos = (int) Math.floor(Math.random() * 10 );
            while (nNombres.contains(pos)) {
                pos = (int) Math.floor(Math.random() * 10 );
            }
            nNombres.push(pos);
        }

        int pos2;
        Stack < Integer > nNombresH = new Stack< Integer >();
        for (int i = 0; i < 5 ; i++) {
            pos2 = (int) Math.floor(Math.random() * 5 );
            while (nNombresH.contains(pos2)) {
                pos2 = (int) Math.floor(Math.random() * 5 );
            }
            nNombresH.push(pos2);
        }

        int pos3;
        Stack < Integer > nNombresM = new Stack< Integer >();
        for (int i = 0; i < 5 ; i++) {
            pos3 = (int) Math.floor(Math.random() * 5 );
            while (nNombresM.contains(pos3)) {
                pos3 = (int) Math.floor(Math.random() * 5 );
            }
            nNombresM.push(pos3);
        }

        // crear 10 personas aleatorias
        for (int i = 0; i < 10; i++) {
            Persona persona;

            if (nNombres.indexOf(i)%2 == 0) {
                String nombre = nombresH[nNombresH.indexOf(x)];
                x=x+1;
                persona = new Hombre(nombre);
            } else {
                String nombre = nombresM[nNombresM.indexOf(y)];
                y=y+1;
                persona = new Mujer(nombre);
            }
            for (int j = 0; j < 4; j++) {
                String atributo = persona.obtenerAtributosPosibles()[random.nextInt(6)];
                while (atributosUsados[j] <= 2 || persona.tieneAtributo(atributo)) {
                    atributo = persona.obtenerAtributosPosibles()[random.nextInt(4)];
                    atributosUsados[j]++;
                }
                atributosUsados[j]++;
                persona.agregarAtributo(atributo, "");
            }
            personas.add(persona);
        }


        for (Persona persona : personas) {
            npersona++;
            HashMap<String, String> atributos = persona.getAtributos();
            String atributosStr = "";
            for (String atributo : atributos.keySet()) {
                atributosStr += atributo + ", ";
            }
            atributosStr = atributosStr.substring(0, atributosStr.length() - 2);
            System.out.println(npersona + " " + persona.getNombre() + ": " + atributosStr);
        }

        // verificar que cada atributo fue utilizado al menos 2 veces
        HashMap<String, Integer> atributosTotales = new HashMap<>();
        for (Persona persona : personas) {
            HashMap<String, String> atributos = persona.getAtributos();
            for (String atributo : atributos.keySet()) {
                atributosTotales.put(atributo, atributosTotales.getOrDefault(atributo, 0) + 1);
            }
        }
        boolean todosUsados = true;
        for (String atributo : atributosTotales.keySet()) {
            if (atributosTotales.get(atributo) < 2) {
                todosUsados = false;
                break;
            }
        }
        if (todosUsados) {
            System.out.println("Cada atributo fue utilizado al menos 2 veces.");
        }

        // elegir persona a adivinar
        int persona = random.nextInt(personas.size());
        Persona personaAdivinar = personas.get(persona);

        // menú de preguntas
        Scanner scanner = new Scanner(System.in);
        int preguntasRealizadas = 0;
        {
            while (preguntasRealizadas < 3) {
                System.out.println("Seleccione una pregunta:");

                String[] atributosPosibles = personaAdivinar.obtenerAtributosPosibles();
                for (int i = 0; i < atributosPosibles.length; i++) {
                    System.out.println((i + 1) + ". ¿Tiene " + atributosPosibles[i] + "?");
                }
                int pregunta = scanner.nextInt();
                while (pregunta < 1 || pregunta > 6 /*|| !personaAdivinar.tieneAtributo(atributosPosibles[pregunta - 1])*/) {
                    System.out.println("Pregunta no válida o respuesta incorrecta, intente de nuevo:");
                    pregunta = scanner.nextInt();
                }
               
                for (int i = 0; i < personas.size(); i++) {
                    if (!personas.get(i).tieneAtributo(atributosPosibles[pregunta - 1])) {
                        personas.remove(i);
                        i--;
                    }
                }
                preguntasRealizadas++;
            }

            // adivinar persona
            System.out.println("¿Quién es la persona elegida? (indique el índice):");
            int respuesta = scanner.nextInt();
            if (respuesta == persona + 1) {
                System.out.println("¡Felicitaciones! Adivinaste la persona.");
            } else {
                System.out.println(persona+1);
                System.out.println("Lo siento, la persona elegida era " + personaAdivinar.getNombre());
            }
        }
    }
}