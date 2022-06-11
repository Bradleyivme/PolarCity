
package Control;
import Inicio.*;
import Post.PostCarrito;
import Post.PostOrdenes;
import Post.PostProductos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class OrdenDePago extends javax.swing.JFrame {
    
    public static VentanaInicio ventanainicio = new VentanaInicio();
    public static Inventario inventario = new Inventario();
    public static Facturacion facturacion = new Facturacion();
    ArrayList<PostCarrito> carrito; //= new ArrayList();
    DefaultTableModel model3;
    public int TotalFinal = 0;
    
    //GET 
    private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
   // GET2 
    private  HttpClient httpClient2 = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
   // post 
    private  HttpClient httpClientpost = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
   // post 
    private  HttpClient httpClientpost2 = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    //detele
    private  HttpClient httpClientdelete = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    
    private final Object[] column = new Object[]{
      "Id", "Codigo", "NProducto", "Descripcion", "Precio"  
    };
    
    private final Object[] column2 = new Object[]{
      "idOrden", "NombreCliente", "Nit", "Total"  
    };
    
    private final Object[] column3 = new Object[]{
      "codigo", "nombreProducto", "precio" 
    };
    
    private final DefaultTableModel model = new DefaultTableModel(column, 0);
    
    private final DefaultTableModel model2 = new DefaultTableModel(column2, 0);
    
    //private final DefaultTableModel model3 = new DefaultTableModel(column2, 0);    
    
    public OrdenDePago() {
        initComponents();
        this.setLocationRelativeTo(null);
        carrito = new ArrayList();
        model3 = new DefaultTableModel(column3, 0);
        ObtenerDatos();
        ObtenerDatosOrden();
    }
    
    final ObjectMapper mapper = new ObjectMapper();
    
    public <T> T convertirObjeto(final String json, final TypeReference<T> reference){
        try {
            return this.mapper.readValue(json, reference);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    final ObjectMapper mapper2 = new ObjectMapper();
    
    public <T> T convertirObjeto2(final String json, final TypeReference<T> reference){
        try {
            return this.mapper2.readValue(json, reference);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(OrdenDePago.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void ObtenerDatos(){
        final HttpRequest requestPost = HttpRequest.newBuilder().GET()
                .uri(URI.create("https://polarcity-app.herokuapp.com/productos")).build();
        try {
            final HttpResponse<String> response = httpClient.send(requestPost, HttpResponse.BodyHandlers.ofString());
            
            List<PostProductos> postsp = convertirObjeto(response.body(), new TypeReference<List<PostProductos>>(){}) ;
            
            postsp.stream().forEach(item -> {
                model.addRow(new Object[] {item.getId(), item.getCodigo(), item.getNombreProducto(), item.getDescripcion(), item.getPrecio()});
            });
            
            this.TableInven2.setModel(model);
                    
        } catch (IOException |InterruptedException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ObtenerDatosOrden(){
        final HttpRequest requestPost = HttpRequest.newBuilder().GET()
                .uri(URI.create("https://polarcity-app.herokuapp.com/ordenes")).build();
        try {
            final HttpResponse<String> response = httpClient2.send(requestPost, HttpResponse.BodyHandlers.ofString());
            
            List<PostOrdenes> postsp = convertirObjeto2(response.body(), new TypeReference<List<PostOrdenes>>(){}) ;
            
            postsp.stream().forEach(item -> {
                model2.addRow(new Object[] {item.getIdOrden(), item.getNombreCliente(), item.getNit(), item.getTotal()});
            });
            
            this.TableOrden.setModel(model2);
                    
        } catch (IOException |InterruptedException ex) {
            Logger.getLogger(OrdenDePago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void postmethod2() throws IOException, InterruptedException{
        String a = txtIO.getText();
        String b = txtNC.getText();
        String c = txtNIC.getText();
        String d = txtTFO.getText();
//        int c = Integer.parseInt(txtNIC.getText());
//        double d = Double.valueOf(txtTFO.getText());
        
        String json2 = new StringBuilder()
                .append("{")
                .append("\"idOrden\":\""+ a +"\",")
                .append("\"nombreCliente\":\""+ b +"\",")
                .append("\"nit\":\""+ c +"\",")
                .append("\"total\":\""+ d +"\"")
                .append("}").toString();

        // add json header
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json2))
                .uri(URI.create("https://polarcity-app.herokuapp.com/ordenes"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient2.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }
    
    
     public void eliminar () throws IOException, InterruptedException{
       
        String a = txtIO.getText();
       HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://polarcity-app.herokuapp.com/ordenes/"+a))
            .header("Content-Type", "application/json").DELETE().build();
          HttpResponse<String> response = httpClientdelete.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

   }

    public void postmethod3() throws IOException, InterruptedException{
        String a = txtIO.getText();
        String b = txtNC.getText();
        String c = txtNIC.getText();
        String d = txtTFO.getText();
//        int c = Integer.parseInt(txtNIC.getText());
//        double d = Double.valueOf(txtTFO.getText());
        
        String json3 = new StringBuilder()
                .append("{")
                .append("\"id\":\""+ a +"\",")
                .append("\"nombreCliente\":\""+ b +"\",")
                .append("\"nit\":\""+ c +"\",")
                .append("\"total\":\""+ d +"\"")
                .append("}").toString();

        // add json header
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json3))
                .uri(URI.create("https://polarcity-app.herokuapp.com/facturas"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClientpost2.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }
    
    public void LLenarTablaCarrito(){
              
        
        model3 = (DefaultTableModel) TableCarr.getModel();
        Object[] ob = new Object[3];
        for(int i = 0; i < carrito.size(); i++){
            ob[0] = carrito.get(i).getCodigo();
            ob[1] = carrito.get(i).getNombreProducto();
            ob[2] = carrito.get(i).getPrecio();
            model3.addRow(ob);
        }
        
        TableCarr.setModel(model3);
        
    }
    
    public void LimpiarTablaCarrito(){
              
        for(int i = 0; i < model3.getRowCount(); i++){
            model3.removeRow(i);
            i = i-1;
        }   
    }
    
    public void VaciarCarrito(){
        carrito.clear();
    }
    
//    public void ObtenerDatosCarrito(){
//        
//        String codigo = txtCod.getText();
//        String nombreProducto = txtNP.getText();
//        int precio = Integer.parseInt(txtPre.getText());
//        
//        PostCarrito c = new PostCarrito();
//        c.setCodigo(codigo);
//        c.setNombreProducto(nombreProducto);
//        c.setPrecio(precio);
//        
//        carrito.add(c);
//    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDespachar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNC = new javax.swing.JTextField();
        txtNIC = new javax.swing.JTextField();
        txtTFO = new javax.swing.JTextField();
        btnOrdenar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtIO = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableInven2 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCarr = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        txtCod = new javax.swing.JTextField();
        txtDes = new javax.swing.JTextField();
        txtNP = new javax.swing.JTextField();
        txtPT = new javax.swing.JTextField();
        txtPre = new javax.swing.JTextField();
        txtCan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableOrden = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 0, 102));
        jButton1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("REGRESAR");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 102));
        jButton2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("INVENTARIO");
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 102));
        jButton3.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("FACTURACION");
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 0, 102));
        jPanel4.setForeground(new java.awt.Color(0, 0, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ORDEN DE PAGO");

        btnDespachar.setBackground(new java.awt.Color(255, 255, 255));
        btnDespachar.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnDespachar.setForeground(new java.awt.Color(0, 0, 102));
        btnDespachar.setText("DESPACHAR");
        btnDespachar.setBorderPainted(false);
        btnDespachar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDespacharActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Id Orden:");

        jLabel9.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nit del cliente:");

        jLabel10.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total final de la orden:");

        txtNC.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        txtNC.setForeground(new java.awt.Color(0, 0, 102));

        txtNIC.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        txtNIC.setForeground(new java.awt.Color(0, 0, 102));

        txtTFO.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        txtTFO.setForeground(new java.awt.Color(102, 0, 0));
        txtTFO.setEnabled(false);

        btnOrdenar.setBackground(new java.awt.Color(255, 255, 255));
        btnOrdenar.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnOrdenar.setForeground(new java.awt.Color(0, 0, 102));
        btnOrdenar.setText("ORDENAR");
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nombre del cliente:");

        txtIO.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        txtIO.setForeground(new java.awt.Color(0, 0, 102));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIO)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDespachar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel10)
                                .addComponent(jLabel9)
                                .addComponent(txtNC)
                                .addComponent(txtNIC)
                                .addComponent(txtTFO)
                                .addComponent(btnOrdenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel11))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTFO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnOrdenar)
                .addGap(18, 18, 18)
                .addComponent(btnDespachar)
                .addGap(34, 34, 34))
        );

        TableInven2.setBackground(new java.awt.Color(0, 0, 102));
        TableInven2.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        TableInven2.setForeground(new java.awt.Color(255, 255, 255));
        TableInven2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableInven2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableInven2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableInven2);

        TableCarr.setBackground(new java.awt.Color(0, 0, 102));
        TableCarr.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        TableCarr.setForeground(new java.awt.Color(255, 255, 255));
        TableCarr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "NombreProducto", "Precio"
            }
        ));
        jScrollPane2.setViewportView(TableCarr);

        btnAgregar.setBackground(new java.awt.Color(0, 0, 102));
        btnAgregar.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("AGREGAR");
        btnAgregar.setBorderPainted(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        txtCod.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        txtCod.setForeground(new java.awt.Color(0, 0, 102));

        txtDes.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        txtDes.setForeground(new java.awt.Color(0, 0, 102));

        txtNP.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        txtNP.setForeground(new java.awt.Color(0, 0, 102));

        txtPT.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        txtPT.setForeground(new java.awt.Color(0, 0, 102));
        txtPT.setEnabled(false);

        txtPre.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        txtPre.setForeground(new java.awt.Color(0, 0, 102));

        txtCan.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        txtCan.setForeground(new java.awt.Color(0, 0, 102));
        txtCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCanActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel2.setText("Codigo:");

        jLabel3.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel3.setText("Nombre del producto:");

        jLabel4.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel4.setText("Descripcion:");

        jLabel5.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel5.setText("Cantidad:");

        jLabel6.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel6.setText("Precio:");

        jLabel7.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel7.setText("Precio total:");

        TableOrden.setBackground(new java.awt.Color(0, 0, 102));
        TableOrden.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        TableOrden.setForeground(new java.awt.Color(255, 255, 255));
        TableOrden.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableOrdenMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableOrden);

        jLabel12.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel12.setText("Carrito de compra:");

        jLabel13.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        jLabel13.setText("Tabla de ordenes:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCan, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPT, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtNP, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtDes, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txtPre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar)
                            .addComponent(txtPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        ventanainicio.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        inventario.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false);
        facturacion.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TableInven2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableInven2MouseClicked
        int f = TableInven2.rowAtPoint(evt.getPoint());
        txtCod.setText(TableInven2.getValueAt(f, 1).toString());
        txtNP.setText(TableInven2.getValueAt(f, 2).toString());
        txtDes.setText(TableInven2.getValueAt(f, 3).toString());
        txtPre.setText(TableInven2.getValueAt(f, 4).toString());
    }//GEN-LAST:event_TableInven2MouseClicked

    private void txtCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCanActionPerformed
        int Cantidad = Integer.parseInt(txtCan.getText());
        int Precio = Integer.parseInt(txtPre.getText());
        int multipli = Cantidad*Precio;
        int TotalInicio = multipli;
        TotalFinal = TotalFinal + TotalInicio;
        txtPT.setText(String.valueOf(TotalInicio));
    }//GEN-LAST:event_txtCanActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        
        String codigo = txtCod.getText();
        String nombreProducto = txtNP.getText();
        int precio = Integer.parseInt(txtPre.getText());
        
        PostCarrito c = new PostCarrito();
        c.setCodigo(codigo);
        c.setNombreProducto(nombreProducto);
        c.setPrecio(precio);
        
        carrito.add(c);
        
        DefaultTableModel modeld = (DefaultTableModel) TableOrden.getModel(); 
          model.setRowCount(0);
        txtTFO.setText(String.valueOf(TotalFinal));
        ObtenerDatos();
        
        //ObtenerDatosCarrito();
        LimpiarTablaCarrito();
        LLenarTablaCarrito();
        
        txtCod.setText("");
        txtNP.setText("");
        txtDes.setText("");
        txtPre.setText("");
        txtCan.setText("");
        txtPT.setText("");
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        
        DefaultTableModel modeld = (DefaultTableModel) TableOrden.getModel(); 
          model2.setRowCount(0);
        try {
            // TODO add your handling code here:
            postmethod2();
        } catch (IOException | InterruptedException ex) {
           
            Logger.getLogger(OrdenDePago.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        TotalFinal = 0;
        txtIO.setText("");
        txtNC.setText("");
        txtNIC.setText("");
        txtTFO.setText("");
        ObtenerDatosOrden();
        LimpiarTablaCarrito();
        VaciarCarrito();
        
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void TableOrdenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableOrdenMouseClicked
      
        int f = TableOrden.rowAtPoint(evt.getPoint());
        txtIO.setText(TableOrden.getValueAt(f, 0).toString());
        txtNC.setText(TableOrden.getValueAt(f, 1).toString());
        txtNIC.setText(TableOrden.getValueAt(f, 2).toString());
        txtTFO.setText(TableOrden.getValueAt(f, 3).toString());
        
    }//GEN-LAST:event_TableOrdenMouseClicked

    private void btnDespacharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDespacharActionPerformed
        DefaultTableModel modeld = (DefaultTableModel) TableOrden.getModel(); 
            model2.setRowCount(0);
        try {
            // TODO add your handling code here:
            postmethod3();
            JOptionPane.showMessageDialog(null, "Datos enviados a Facturacion exitosamente.");
            eliminar();
        } catch (IOException | InterruptedException ex) {
           
            Logger.getLogger(OrdenDePago.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al enviar datos.");
        }
        ObtenerDatosOrden();
        
    }//GEN-LAST:event_btnDespacharActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrdenDePago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenDePago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenDePago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenDePago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrdenDePago().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableCarr;
    private javax.swing.JTable TableInven2;
    private javax.swing.JTable TableOrden;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnDespachar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCan;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtDes;
    private javax.swing.JTextField txtIO;
    private javax.swing.JTextField txtNC;
    private javax.swing.JTextField txtNIC;
    private javax.swing.JTextField txtNP;
    private javax.swing.JTextField txtPT;
    private javax.swing.JTextField txtPre;
    private javax.swing.JTextField txtTFO;
    // End of variables declaration//GEN-END:variables
}
