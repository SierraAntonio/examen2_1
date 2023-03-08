package examen2;

class Hombre extends Persona {
public Hombre(String nombre) {
super(nombre); }
public String[] obtenerAtributosPosibles() {
    return new String[]{"Barba", "Bigote", "Ojos grandes", "Lentes", "tatuajes", "Cabello largo","orejas firmes"};
}

}
