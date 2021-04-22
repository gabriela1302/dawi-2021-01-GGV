package org;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ore.ciberfarma.modelo.Usuario;

import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;

public class CrudUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JButton btnRegistrar;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JTextArea txtS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrudUsuario frame = new CrudUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CrudUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(98, 26, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(98, 57, 86, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(98, 88, 86, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		btnRegistrar.setBounds(274, 25, 89, 23);
		contentPane.add(btnRegistrar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(274, 56, 89, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblNewLabel = new JLabel("Codigo:");
		lblNewLabel.setBounds(26, 29, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(26, 60, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Apellido:");
		lblNewLabel_2.setBounds(26, 91, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 371, 131);
		contentPane.add(scrollPane);
		
		txtS = new JTextArea();
		scrollPane.setViewportView(txtS);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(274, 87, 89, 23);
		contentPane.add(btnListado);
	}

	protected void registrar() {
		String nombre = leerNombre();
		
	}

	private String leerNombre() {
		if(!txtNombre.getText().matches("")) {
			return null;
		}
		return txtNombre.getText();
	}

	void listado() {
		// Obtener un listado de los Usuarios
		EntityManagerFactory fabrica =Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em=fabrica.createEntityManager();
		
		//TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAll", Usuario.class);
		//List<Usuario> lstUsuarios = consulta.getResultList();
		
		TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAllWithType", Usuario.class);
		consulta.setParameter("xtipo", 1);
		List<Usuario> lstUsuarios = consulta.getResultList();
		em.close();
		
		//pasar el listado a txt,..
		for(Usuario u : lstUsuarios) {
			txtS.append(u.getCodigo()+"\t"+u.getNombre()+"\t"+u.getApellido()+"\n");
		}
	}
	
	void buscar() {
		//leer el codigo
		int codigo =leerCodigo();
		//buscae en la tabla, para obtener un usuario
		EntityManagerFactory fabrica =Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em=fabrica.createEntityManager();
		
		Usuario u = em.find(Usuario.class, codigo);
		em.close();
		//si existe lo muestra en los campos, sino avisa
		if(u==null) {
			aviso("Usuario "+codigo+" no existe!!!");
		}else {
			txtNombre.setText(u.getNombre());
			txtApellido.setText(u.getApellido());
		}
	}
	
	private void aviso(String msg) {
		JOptionPane.showMessageDialog(this, msg,"Aviso del sistema",JOptionPane.WARNING_MESSAGE);
		
	}

	private int leerCodigo() {
		return Integer.parseInt(txtCodigo.getText());
	}
}
