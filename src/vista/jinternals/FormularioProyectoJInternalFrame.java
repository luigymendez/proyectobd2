package vista.jinternals;

import control.objects.ControlHorariosProyecto;
import control.objects.ControlProyectos;
import controller.AnteproyectoJpaController;
import controller.DocentesJpaController;
import controller.EmpresasJpaController;
import controller.EstadosJpaController;
import controller.HorariosJpaController;
import controller.ProyectosJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Anteproyecto;
import modelo.Docentes;
import modelo.Empresas;
import modelo.Estados;
import modelo.Horarios;
import modelo.Integrantes;
import modelo.Proyectos;
import utilerias.EnviarEmail;
import utilerias.Formateador;
import utilerias.TecladoException;
import vista.MDIAplicacion;

public class FormularioProyectoJInternalFrame extends javax.swing.JInternalFrame {

    EstadosJpaController estadosJpaController = new EstadosJpaController();
    ProyectosJpaController proyectosJpaController = new ProyectosJpaController();
    EmpresasJpaController empresasJpaController = new EmpresasJpaController();
    AnteproyectoJpaController anteproyectoJpaController = new AnteproyectoJpaController();
    DocentesJpaController docentesJpaController = new DocentesJpaController();
    HorariosJpaController horariosJpaController = new HorariosJpaController();
    Proyectos proyectoToModify;
    private Proyectos proyectoActual;
    private Docentes directorAsignado;

    private List<Estados> listaEstadosProyecto;
    private List<Empresas> listaEmpresas;
    private List<Anteproyecto> listaAnteproyectos;
    //Estos index sirven para ir iterando la lista cuando se haga una busqueda de empresa o anteproyecto
    int indexListaEmpresa = 0;
    int indexListaAntepr = 0;
    ControlProyectos controlProyectos = new ControlProyectos();
    ControlHorariosProyecto controlHorariosProyecto = new ControlHorariosProyecto();

    public FormularioProyectoJInternalFrame(Proyectos proyecto) {
        initComponents();
        proyectoToModify = proyecto;
        //Cargamos los estados del modulo proyecto
        cargarEstadosProyecto();
        if (proyectoToModify != null) {
            //Si el proyecto a modificar es diferente de null quiere decir que se esta actualizando
            // y por lo tanto hay que llenar el formulario
            String split[] = proyectoToModify.getPeriodo().split("-");
            int anio = TecladoException.getEntero(split[0]);
            jYearChooser2.setYear(anio);
            jComboBoxPeriodo.setSelectedItem(split[1]);
            jTextFieldGrupo.setText(proyectoToModify.getGrupo());
            fechaRecepcionDateChooser.setDate(proyectoToModify.getFechaRecepcion());
            Calendar cal = Formateador.dateToCalendar(proyectoToModify.getHoraRecepcion());
            jTextFieldHora1.setText("" + cal.get(Calendar.HOUR_OF_DAY));
            jTextFieldMinuto1.setText("" + cal.get(Calendar.MINUTE));
            jTextFieldEmpresa.setText(proyectoToModify.getEmpresasId().getNombre());
            jTextFieldAnteproyecto.setText(proyectoToModify.getAnteproyectoId().getTitulo());
            if (proyectoToModify.getNotaDefinitiva() != null) {
                jTextFieldNota.setText("" + proyectoToModify.getNotaDefinitiva());
            }
            jComboBoxEstado.setSelectedItem(proyectoToModify.getEstadosId().getNombreEstado());
            jButtonOK.setText("Modificar");
            Horarios horario = proyectoToModify.getHorariosId();
            jComboBoxDiaHorario.setSelectedItem(horario.getDia());
            dateChooserFechaInicioHorario.setDate(horario.getFechaInicioAsesoria());
            dateChooserFechaFinHorario.setDate(horario.getFechaTerminacionAsesoria());
            Calendar cal1 = Formateador.dateToCalendar(horario.getHoraInicio());
            Calendar cal2 = Formateador.dateToCalendar(horario.getHoraFinalizacion());
            jTextFieldHoraInicioHorario.setText("" + cal1.get(Calendar.HOUR_OF_DAY));
            jTextFieldMinutoInicioHorario.setText("" + cal.get(Calendar.MINUTE));
            jTextFieldHoraFinHorario.setText("" + cal2.get(Calendar.HOUR_OF_DAY));
            jTextFieldMinutoFinHorario.setText("" + cal2.get(Calendar.MINUTE));
            Docentes docenteDirector = proyectoToModify.getDocentesIdentificacion();
            if (docenteDirector != null) {
                jTextFieldIdDirector.setText("" + docenteDirector.getIdentificacion());
                jTextFieldNombreDirector.setText("" + docenteDirector.getNombres());
            }
        }

    }

    public final void cargarEstadosProyecto() {
        listaEstadosProyecto = estadosJpaController.getEstadosByModulo("proyectos");
        for (Estados estados : listaEstadosProyecto) {
            jComboBoxEstado.addItem(estados.getNombreEstado());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        archivoDialog = new javax.swing.JDialog();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxDiaHorario = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        dateChooserFechaInicioHorario = new com.toedter.calendar.JDateChooser();
        dateChooserFechaFinHorario = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldHoraInicioHorario = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldMinutoInicioHorario = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldHoraFinHorario = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldMinutoFinHorario = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldGrupo = new javax.swing.JTextField();
        jTextFieldAnteproyecto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxEstado = new javax.swing.JComboBox();
        jTextFieldNota = new javax.swing.JTextField();
        jTextFieldEmpresa = new javax.swing.JTextField();
        buttonBuscarAntProy = new javax.swing.JButton();
        jComboBoxPeriodo = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jYearChooser2 = new com.toedter.calendar.JYearChooser();
        buttonBuscarEmpresa = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        fechaRecepcionDateChooser = new com.toedter.calendar.JDateChooser();
        jTextFieldMinuto1 = new javax.swing.JTextField();
        jTextFieldHora1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButtonOK = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldDirector = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldIdDirector = new javax.swing.JTextField();
        jTextFieldNombreDirector = new javax.swing.JTextField();
        buttonBuscarDirector = new javax.swing.JButton();
        jButtonAsignarDirector = new javax.swing.JButton();

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout archivoDialogLayout = new javax.swing.GroupLayout(archivoDialog.getContentPane());
        archivoDialog.getContentPane().setLayout(archivoDialogLayout);
        archivoDialogLayout.setHorizontalGroup(
            archivoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(archivoDialogLayout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        archivoDialogLayout.setVerticalGroup(
            archivoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(archivoDialogLayout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setClosable(true);
        setMaximizable(true);
        setTitle("Formulario Proyecto");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Horario de asesorías"));

        jLabel4.setText("Día");

        jComboBoxDiaHorario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" }));

        jLabel9.setText("Fecha inicio");

        jLabel10.setText("Fecha fin");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Apps-clock-icon.png"))); // NOI18N

        jLabel18.setText("Hora inicio");

        jLabel14.setText(":");

        jLabel19.setText("Hora fin");
        jLabel19.setToolTipText("");

        jLabel20.setText(":");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Apps-clock-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserFechaFinHorario, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(dateChooserFechaInicioHorario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldHoraFinHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldHoraInicioHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldMinutoInicioHorario, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(jTextFieldMinutoFinHorario)))
                    .addComponent(jComboBoxDiaHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jTextFieldHoraFinHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldMinutoFinHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxDiaHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(dateChooserFechaInicioHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel18)
                                .addComponent(jTextFieldHoraInicioHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldMinutoInicioHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateChooserFechaFinHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addComponent(jLabel21))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Registro proyecto"));

        jLabel1.setText("Período:");

        jLabel2.setText("Grupo:");

        jLabel3.setText("Anteproyecto :");

        jTextFieldGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldGrupoActionPerformed(evt);
            }
        });

        jTextFieldAnteproyecto.setToolTipText("Una vez se haya hecho una búsqueda puede ir cambiando el anteproyecto con \nlas flechas arriba y abajo");
        jTextFieldAnteproyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAnteproyectoKeyPressed(evt);
            }
        });

        jLabel5.setText("Empresa  beneficiaria:");
        jLabel5.setToolTipText("Empresa u organización que se beneficia por el proyecto. ");

        jLabel7.setText("Estado proyecto: ");

        jLabel8.setText("Nota definitiva:");

        jTextFieldNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNotaActionPerformed(evt);
            }
        });

        jTextFieldEmpresa.setToolTipText("Una vez se haya hecho una búsqueda puede ir cambiando la empresa con \nlas flechas arriba y abajo");
        jTextFieldEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldEmpresaKeyPressed(evt);
            }
        });

        buttonBuscarAntProy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Start-Menu-Search-icon.png"))); // NOI18N
        buttonBuscarAntProy.setToolTipText("Buscar anteproyectos");
        buttonBuscarAntProy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarAntProyActionPerformed(evt);
            }
        });

        jComboBoxPeriodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02" }));

        jLabel12.setText("-");

        buttonBuscarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Start-Menu-Search-icon.png"))); // NOI18N
        buttonBuscarEmpresa.setToolTipText("Buscar empresas");
        buttonBuscarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarEmpresaActionPerformed(evt);
            }
        });

        jLabel6.setText("Fecha recepción:");

        jLabel15.setText("Hora recepción:");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Apps-clock-icon.png"))); // NOI18N

        jLabel13.setText(":");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextFieldHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMinuto1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldGrupo)
                    .addComponent(fechaRecepcionDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 69, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonBuscarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextFieldAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonBuscarAntProy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBoxEstado, javax.swing.GroupLayout.Alignment.LEADING, 0, 110, Short.MAX_VALUE)
                                .addComponent(jTextFieldNota, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonBuscarEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jComboBoxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel5)
                        .addComponent(jTextFieldEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldAnteproyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextFieldGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonBuscarAntProy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(fechaRecepcionDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(jTextFieldHora1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel16)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldMinuto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(1, 1, 1)))
                        .addGap(22, 22, 22))))
        );

        jButtonOK.setText("Guardar");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Director asignado"));

        jLabel11.setText("Para asignar un director al proyecto simplemente búsquelo por el nombre o identificación. Si aun no conoce quien será el ");

        jLabel22.setText("director puede asignarlo más adelante.");

        jLabel23.setText("Datos del director");

        jTextFieldDirector.setToolTipText("Identificacion / nombre");

        jLabel24.setText("Identificación");

        jLabel25.setText("Nombre");

        jTextFieldIdDirector.setEditable(false);

        jTextFieldNombreDirector.setEditable(false);

        buttonBuscarDirector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Start-Menu-Search-icon.png"))); // NOI18N
        buttonBuscarDirector.setToolTipText("Buscar director");
        buttonBuscarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarDirectorActionPerformed(evt);
            }
        });

        jButtonAsignarDirector.setText("Asignar");
        jButtonAsignarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAsignarDirectorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel22)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextFieldDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonBuscarDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAsignarDirector)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(45, 45, 45)
                                .addComponent(jTextFieldNombreDirector))
                            .addComponent(jLabel23)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldIdDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGap(8, 8, 8)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextFieldDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24)
                                .addComponent(jTextFieldIdDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonAsignarDirector))
                            .addComponent(buttonBuscarDirector))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jTextFieldNombreDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNotaActionPerformed

    private void jTextFieldGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldGrupoActionPerformed

    private void buttonBuscarAntProyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarAntProyActionPerformed

        listaAnteproyectos = anteproyectoJpaController.encontrarCoincidenciasPorTitulo(jTextFieldAnteproyecto.getText());
        indexListaAntepr = 0;
        if (listaAnteproyectos.size() > 0) {
            jTextFieldAnteproyecto.setText(listaAnteproyectos.get(0).getTitulo());
            System.out.println("El tamaño de la lista es :" + listaAnteproyectos.size());
        }
    }//GEN-LAST:event_buttonBuscarAntProyActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void buttonBuscarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarEmpresaActionPerformed
        listaEmpresas = empresasJpaController.encontrarCoincidenciasPorNombre(jTextFieldEmpresa.getText());
        indexListaEmpresa = 0;
        if (listaEmpresas.size() > 0) {
            jTextFieldEmpresa.setText(listaEmpresas.get(0).getNombre());
            System.out.println("El tamaño de la lista es :" + listaEmpresas.size());
        }
    }//GEN-LAST:event_buttonBuscarEmpresaActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        Proyectos proyecto = null;
        Horarios horario = null;

        Object argsHorario[] = new Object[7];
        argsHorario[0] = jComboBoxDiaHorario.getSelectedItem();
        argsHorario[1] = dateChooserFechaInicioHorario.getDate();
        argsHorario[2] = dateChooserFechaFinHorario.getDate();
        argsHorario[3] = jTextFieldHoraInicioHorario.getText();
        argsHorario[4] = jTextFieldMinutoInicioHorario.getText();;
        argsHorario[5] = jTextFieldHoraFinHorario.getText();;
        argsHorario[6] = jTextFieldMinutoFinHorario.getText();;

        Object args[] = new Object[10];
        args[0] = jYearChooser2.getYear();
        args[1] = jComboBoxPeriodo.getSelectedItem().toString();
        args[2] = jTextFieldGrupo.getText();
        args[3] = fechaRecepcionDateChooser.getDate();
        args[4] = jTextFieldHora1.getText();
        args[5] = jTextFieldMinuto1.getText();
        args[6] = jTextFieldEmpresa.getText();
        args[7] = jTextFieldAnteproyecto.getText();
        args[8] = jTextFieldNota.getText();
        args[9] = jComboBoxEstado.getSelectedItem().toString();

        if (proyectoToModify == null) {
            //Se esta guardando

            proyecto = controlProyectos.crearObjetoProyecto(args, listaEstadosProyecto);
            horario = controlHorariosProyecto.crearObjetoHorario(argsHorario);
            //Una vez tenemos el horario lo asignamos al proyecto

            if (proyecto != null && horario != null) {
                //proyecto.setHorariosId(horario);
                try {
                    //Primero creamos el horario de asesoria para el proyecto
                    //horario.setDocentesIdentificacion(docentesJpaController.findDocentes(BigDecimal.ONE));
                    horario.setId(horariosJpaController.getNextID());
                    horariosJpaController.create(horario);
                    //Una vez creado el horario , debemos pasarlo al proyecto como foranea , pero debemos
                    //hacer una consulta del el ultimo horario que acabamos de insertar , para que se traiga el registro
                    //con todos los datos y su id. Lo hacemos de una manera ineficiente .. obteniendo el ultimo registro
                    List<Horarios> listaHorarios = horariosJpaController.findHorariosEntities();
                    proyecto.setHorariosId(listaHorarios.get(listaHorarios.size() - 1));
                    proyecto.setId(proyectosJpaController.getNextID());
                    proyectosJpaController.create(proyecto);
                    JOptionPane.showMessageDialog(null, "Se ha registrado correctamente el proyecto y su horario de asesorias.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    this.proyectoActual = proyecto;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error inesperado : " + ex.getMessage(), "Mensaje error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            //Se esta modificando

             proyecto = controlProyectos.modificarObjetoProyecto(args, listaEstadosProyecto, proyectoToModify);
             horario = controlHorariosProyecto.modificarObjetoHorario(argsHorario, proyectoToModify.getHorariosId());
            //Una vez tenemos el horario lo asignamos al proyecto

            if (proyecto != null && horario != null) {
                proyecto.setHorariosId(horario);
                try {
                    //Primero creamos el horario de asesoria para el proyecto
                    horariosJpaController.edit(horario);
                    proyectosJpaController.edit(proyecto);
                    JOptionPane.showMessageDialog(null, "Se ha modificado correctamente la información del proyecto", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error inesperado : " + ex.getMessage(), "Mensaje error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }


    }//GEN-LAST:event_jButtonOKActionPerformed

    private void jTextFieldEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmpresaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (listaEmpresas.size() > 0) {
                indexListaEmpresa++;
                try {
                    jTextFieldEmpresa.setText(listaEmpresas.get(indexListaEmpresa).getNombre());
                } catch (IndexOutOfBoundsException e) {
                    indexListaEmpresa = 0;
                    System.err.println("Fuera de rango por debajo");
                }
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (listaEmpresas.size() > 0) {
                indexListaEmpresa--;
                try {
                    jTextFieldEmpresa.setText(listaEmpresas.get(indexListaEmpresa).getNombre());
                } catch (IndexOutOfBoundsException e) {
                    indexListaEmpresa = 0;
                    System.err.println("Fuera de rango por arriba");
                }

            }
        }

    }//GEN-LAST:event_jTextFieldEmpresaKeyPressed

    private void jTextFieldAnteproyectoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAnteproyectoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (listaAnteproyectos.size() > 0) {
                indexListaAntepr++;
                try {
                    jTextFieldAnteproyecto.setText(listaAnteproyectos.get(indexListaAntepr).getTitulo());
                } catch (IndexOutOfBoundsException e) {
                    indexListaAntepr = 0;
                    System.err.println("Fuera de rango por debajo");
                }
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (listaAnteproyectos.size() > 0) {
                indexListaAntepr--;
                try {
                    jTextFieldAnteproyecto.setText(listaAnteproyectos.get(indexListaAntepr).getTitulo());
                } catch (IndexOutOfBoundsException e) {
                    indexListaAntepr = 0;
                    System.err.println("Fuera de rango por arriba");
                }
            }
        }
    }//GEN-LAST:event_jTextFieldAnteproyectoKeyPressed

    private void buttonBuscarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarDirectorActionPerformed
        List<Docentes> listDocentes = docentesJpaController.encontrarCoincidenciasPorIdNombre(jTextFieldDirector.getText());
        if (listDocentes.size() > 0) {
            jTextFieldDirector.setText(listDocentes.get(0).getNombres());
            directorAsignado = listDocentes.get(0);
        }

    }//GEN-LAST:event_buttonBuscarDirectorActionPerformed

    private void jButtonAsignarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAsignarDirectorActionPerformed

        if (proyectoToModify == null) {
            if (proyectoActual != null) {
                asignarDirectorYEnviarEmails(proyectoActual);
            } else {
                JOptionPane.showMessageDialog(null, "Primero debe crear el proyecto y luego si asignar el director");
            }
        } else {
            asignarDirectorYEnviarEmails(proyectoToModify);
        }

    }//GEN-LAST:event_jButtonAsignarDirectorActionPerformed

    private void asignarDirectorYEnviarEmails(Proyectos proyectoActual) {
        EnviarEmail mail = new EnviarEmail();
        Boolean et;
        proyectoActual.setDocentesIdentificacion(directorAsignado);
        try {
            proyectosJpaController.edit(proyectoActual);
            // if (busqueda.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            Collection<Integrantes> listaIntegrantes = proyectoActual.getAnteproyectoId().getPropuestasId().getIdeasId().getIntegrantesCollection();
            String mensajeIntegrantes = "Titulo del proyecto : " + proyectoActual.getAnteproyectoId().getTitulo() + "\n\nIntegrantes :\n";
            int i = 0;
            for (Integrantes integrantes : listaIntegrantes) {
                mensajeIntegrantes += "Nombre : " + integrantes.getNombres() + " " + integrantes.getApellidos() + "     Email : " + integrantes.getEmail1() + "\n";
                i++;
            }
            mensajeIntegrantes += proyectoActual.getHorariosId().toString();
            //Envamos un correo al director asignado y luego a todos los integrantes del proyecto
            mail.send(directorAsignado.getCorreoInstitucional(), "ASIGNACIÓN PROYECTO GRADO PARA DIRECCIÓN", mensajeIntegrantes);
            mensajeIntegrantes += "\n\nDirector asignado:\nNombre : " + directorAsignado.getNombres() + "\nCorreo : " + directorAsignado.getCorreoInstitucional();
            //Ahora se le envia a cada integrante un email
            for (Integrantes integrantes : listaIntegrantes) {
                mail.send(integrantes.getEmail1(), "ASIGNACIÓN DIRECTOR", mensajeIntegrantes);
            }
            JOptionPane.showMessageDialog(null, "Se han enviado los emails a los estudiantes y al director", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        } catch (NonexistentEntityException ex) {
            System.err.println("Error"+ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error"+ex.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog archivoDialog;
    private javax.swing.JButton buttonBuscarAntProy;
    private javax.swing.JButton buttonBuscarDirector;
    private javax.swing.JButton buttonBuscarEmpresa;
    private com.toedter.calendar.JDateChooser dateChooserFechaFinHorario;
    private com.toedter.calendar.JDateChooser dateChooserFechaInicioHorario;
    private com.toedter.calendar.JDateChooser fechaRecepcionDateChooser;
    private javax.swing.JButton jButtonAsignarDirector;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JComboBox jComboBoxDiaHorario;
    private javax.swing.JComboBox jComboBoxEstado;
    private javax.swing.JComboBox jComboBoxPeriodo;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldAnteproyecto;
    private javax.swing.JTextField jTextFieldDirector;
    private javax.swing.JTextField jTextFieldEmpresa;
    private javax.swing.JTextField jTextFieldGrupo;
    private javax.swing.JTextField jTextFieldHora1;
    private javax.swing.JTextField jTextFieldHoraFinHorario;
    private javax.swing.JTextField jTextFieldHoraInicioHorario;
    private javax.swing.JTextField jTextFieldIdDirector;
    private javax.swing.JTextField jTextFieldMinuto1;
    private javax.swing.JTextField jTextFieldMinutoFinHorario;
    private javax.swing.JTextField jTextFieldMinutoInicioHorario;
    private javax.swing.JTextField jTextFieldNombreDirector;
    private javax.swing.JTextField jTextFieldNota;
    private com.toedter.calendar.JYearChooser jYearChooser2;
    // End of variables declaration//GEN-END:variables
}
