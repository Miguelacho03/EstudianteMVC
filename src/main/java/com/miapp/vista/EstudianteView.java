package com.miapp.vista;

import com.miapp.controlador.EstudianteController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;

public class EstudianteView extends JFrame {
    // Componentes de Búsqueda y Mostrar Todos (Enunciado 3)
    private JTextField txtBuscarNombre;
    private JButton btnBuscar;
    private JButton btnMostrarTodos;

    // Componentes de Agregar Estudiante (Enunciado 1)
    private JTextField txtAddNombre;
    private JTextField txtAddCarrera;
    private JTextField txtAddPromedio;
    private JButton btnAgregar;

    // Componentes de Ordenamiento (Enunciado 2)
    private JComboBox<String> cbCriterioOrden;
    private JButton btnOrdenar;

    // Componentes de la Tabla
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado;
    private EstudianteController controlador;

    public EstudianteView() {
        setTitle("Búsqueda de Estudiantes — MVC NetBeans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Superior Contenedor
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

        // 1. Subpanel Buscar estudiante + Mostrar todos (Enunciado 3)
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar estudiante"));
        
        panelBusqueda.add(new JLabel("Nombre:"));
        txtBuscarNombre = new JTextField(15);
        panelBusqueda.add(txtBuscarNombre);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        panelBusqueda.add(btnBuscar);

        btnMostrarTodos = new JButton("Mostrar todos");
        panelBusqueda.add(btnMostrarTodos);

        // 2. Subpanel Agregar estudiante (Enunciado 1)
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar estudiante"));

        panelAgregar.add(new JLabel("Nombre:"));
        txtAddNombre = new JTextField(12);
        panelAgregar.add(txtAddNombre);

        panelAgregar.add(new JLabel("Carrera:"));
        txtAddCarrera = new JTextField(12);
        panelAgregar.add(txtAddCarrera);

        panelAgregar.add(new JLabel("Promedio:"));
        txtAddPromedio = new JTextField(5);
        panelAgregar.add(txtAddPromedio);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        panelAgregar.add(btnAgregar);

        // 3. Subpanel Ordenar resultados (Enunciado 2)
        JPanel panelOrdenar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelOrdenar.setBorder(BorderFactory.createTitledBorder("Ordenar resultados"));

        panelOrdenar.add(new JLabel("Criterio:"));
        cbCriterioOrden = new JComboBox<>(new String[]{"Nombre", "Promedio"});
        panelOrdenar.add(cbCriterioOrden);

        btnOrdenar = new JButton("Ordenar");
        panelOrdenar.add(btnOrdenar);

        // Agregar los tres subpaneles al panel superior
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelAgregar);
        panelSuperior.add(panelOrdenar);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel Central: Tabla de Resultados
        String[] columnas = {"ID", "Nombre", "Carrera", "Promedio"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        // Panel Inferior: Barra de Estado
        lblEstado = new JLabel("Listo.");
        add(lblEstado, BorderLayout.SOUTH);

        // Asignación de Eventos
        btnBuscar.addActionListener(e -> {
            if (controlador != null) {
                controlador.buscarPorNombre(txtBuscarNombre.getText());
            }
        });

        btnMostrarTodos.addActionListener(e -> {
            if (controlador != null) {
                controlador.mostrarTodos();
            }
        });

        btnAgregar.addActionListener(e -> {
            if (controlador != null) {
                controlador.agregarEstudiante(
                    txtAddNombre.getText(),
                    txtAddCarrera.getText(),
                    txtAddPromedio.getText()
                );
                txtAddNombre.setText("");
                txtAddCarrera.setText("");
                txtAddPromedio.setText("");
            }
        });

        btnOrdenar.addActionListener(e -> {
            if (controlador != null) {
                String criterio = (String) cbCriterioOrden.getSelectedItem();
                controlador.ordenarPor(criterio);
            }
        });
    }

    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
    }

    public void mostrarEstudiantes(List<Object[]> estudiantes) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : estudiantes) {
            modeloTabla.addRow(fila);
        }
        lblEstado.setText("Se encontraron " + estudiantes.size() + " estudiante(s).");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}