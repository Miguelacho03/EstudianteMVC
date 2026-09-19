package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.vista.EstudianteView;
import java.util.ArrayList;
import java.util.List;

public class EstudianteController {
    private List<Estudiante> estudiantes;
    private EstudianteView vista;
    private List<Object[]> ultimosResultados;
    private boolean ordenAscendente = true;

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        this.estudiantes = new ArrayList<>();
        this.ultimosResultados = new ArrayList<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        estudiantes.add(new Estudiante(1, "Ana García", "Ingeniería de Sistemas", 4.50));
        estudiantes.add(new Estudiante(2, "Carlos López", "Ingeniería Civil", 3.80));
        estudiantes.add(new Estudiante(3, "María Rodríguez", "Medicina", 4.90));
        estudiantes.add(new Estudiante(4, "José Martínez", "Derecho", 3.50));
        estudiantes.add(new Estudiante(5, "Laura Sánchez", "Administración", 4.10));
        estudiantes.add(new Estudiante(6, "Andrés Torres", "Ingeniería de Sistemas", 3.90));
        estudiantes.add(new Estudiante(7, "Valentina Gómez", "Psicología", 4.30));
        estudiantes.add(new Estudiante(8, "Luis Herrera", "Economía", 3.70));
        estudiantes.add(new Estudiante(9, "Sofía Díaz", "Ingeniería Civil", 4.60));
        estudiantes.add(new Estudiante(10, "Juliana Morales", "Medicina", 4.80));
        estudiantes.add(new Estudiante(11, "Ana Milena Ruiz", "Derecho", 4.00));
        estudiantes.add(new Estudiante(12, "Carlos Andrés Paz", "Administración", 3.60));
    }

    // Método para buscar por nombre
    public void buscarPorNombre(String nombre) {
        ultimosResultados.clear();
        for (Estudiante e : estudiantes) {
            if (e.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                ultimosResultados.add(new Object[]{e.getId(), e.getNombre(), e.getCarrera(), e.getPromedio()});
            }
        }
        vista.mostrarEstudiantes(ultimosResultados);
    }

    // Enunciado 3: Mostrar todos los estudiantes
    public void mostrarTodos() {
        ultimosResultados.clear();
        for (Estudiante e : estudiantes) {
            ultimosResultados.add(new Object[]{e.getId(), e.getNombre(), e.getCarrera(), e.getPromedio()});
        }
        vista.mostrarEstudiantes(ultimosResultados);
    }

    // Enunciado 1: Registrar un nuevo estudiante
    public void agregarEstudiante(String nombre, String carrera, String promedioText) {
        if (nombre == null || nombre.trim().isEmpty()) {
            vista.mostrarError("El nombre no puede estar vacío.");
            return;
        }

        if (carrera == null || carrera.trim().isEmpty()) {
            vista.mostrarError("La carrera no puede estar vacía.");
            return;
        }

        double promedio;
        try {
            promedio = Double.parseDouble(promedioText.replace(",", "."));
        } catch (NumberFormatException e) {
            vista.mostrarError("El promedio debe ser un número válido.");
            return;
        }

        if (promedio < 0.0 || promedio > 5.0) {
            vista.mostrarError("El promedio debe estar entre 0.0 y 5.0.");
            return;
        }

        int nuevoId = estudiantes.size() + 1;
        Estudiante nuevo = new Estudiante(nuevoId, nombre.trim(), carrera.trim(), promedio);
        estudiantes.add(nuevo);

        vista.mostrarMensaje("Estudiante agregado con éxito.");
        mostrarTodos(); // Refresca la tabla y actualiza ultimosResultados
    }

    // Enunciado 2: Ordenar resultados por criterio (alternando orden)
    public void ordenarPor(String criterio) {
        if (ultimosResultados == null || ultimosResultados.isEmpty()) {
            vista.mostrarError("No hay resultados en la tabla para ordenar.");
            return;
        }

        if ("Nombre".equalsIgnoreCase(criterio)) {
            ultimosResultados.sort((a, b) -> {
                String nomA = (String) a[1];
                String nomB = (String) b[1];
                return ordenAscendente ? nomA.compareToIgnoreCase(nomB) : nomB.compareToIgnoreCase(nomA);
            });
        } else if ("Promedio".equalsIgnoreCase(criterio)) {
            ultimosResultados.sort((a, b) -> {
                Double promA = (Double) a[3];
                Double promB = (Double) b[3];
                return ordenAscendente ? promA.compareTo(promB) : promB.compareTo(promA);
            });
        }

        ordenAscendente = !ordenAscendente; // Alterna entre ascendente y descendente
        vista.mostrarEstudiantes(ultimosResultados);
    }
}