package examen2;

class Mujer extends Persona {
public Mujer(String nombre) {
super(nombre);
}
public String[] obtenerAtributosPosibles() {
    return new String[]{"Collar", "Pulsera", "Anillo", "Pendientes", "Lentes", "Cabello corto"};
}

}