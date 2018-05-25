/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.controller;

import com.google.gson.Gson;
import cr.ac.una.escinf.proyectoaerolinea.data.service.ServicioUsuario;
import cr.ac.una.escinf.proyectoaerolinea.models.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author slon
 */
public class UsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {   
            //String para guardar el JSON generaro por al libreria GSON
            String json;
           
            //Se crea el objeto
            Usuario usuario = new Usuario();
            
            ServicioUsuario su = new ServicioUsuario();
            
            Thread.sleep(1000);
            
            String accion = request.getParameter("accion");
            
            switch (accion) {                   
                case "insertar":
                    usuario.setNombre(request.getParameter("nombre"));
                    usuario.setApellidos(request.getParameter("apellidos"));
                    usuario.setCorreo(request.getParameter("correo"));
                    usuario.setFechaNacimiento(request.getParameter("fechaNacimiento"));
                    usuario.setDireccion(request.getParameter("direccion")); 
                    usuario.setCelular(request.getParameter("celular"));
                    usuario.setTelefono(request.getParameter("telefono"));
                    usuario.setUsuario(request.getParameter("usuario"));
                    usuario.setContrasena(request.getParameter("contrasena")); 
                          
                    su.insertarUsuario(usuario);

                    out.print("C~El objeto fue ingresado correctamente");
                    break;
         
                case "actualizar":
                    usuario.setNombre(request.getParameter("nombre"));
                    usuario.setApellidos(request.getParameter("apellidos"));
                    usuario.setCorreo(request.getParameter("correo"));
                    usuario.setFechaNacimiento(request.getParameter("fechaNacimiento"));
                    usuario.setDireccion(request.getParameter("direccion")); 
                    usuario.setCelular(request.getParameter("celular"));
                    usuario.setTelefono(request.getParameter("telefono"));
                    usuario.setUsuario(request.getParameter("usuario"));
                    usuario.setContrasena(request.getParameter("contrasena")); 

                    su.actualizarUsuario(usuario);

                    out.print("C~El objeto fue actualizado correctamente");
                    break;
                    
                case "buscar":
                    //se consulta al usuario por ID
                    usuario = su.buscarUsuario(request.getParameter("usuario"));
                    
                    //se pasa la informacion del objeto a formato JSON
                    json = new Gson().toJson(usuario);
                    out.print(json);
                    break;
                    
                default:
                    out.print("E~No se indicó la acción que se desea realizare");
                    break;
            }
        }  catch (Exception e) {
            out.print("E~" + e.getMessage());
        } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
