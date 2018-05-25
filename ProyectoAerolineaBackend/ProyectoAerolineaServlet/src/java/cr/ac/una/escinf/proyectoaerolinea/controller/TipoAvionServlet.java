/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.controller;

import com.google.gson.Gson;
import cr.ac.una.escinf.proyectoaerolinea.data.service.ServicioTipoAvion;
import cr.ac.una.escinf.proyectoaerolinea.models.TipoAvion;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author slon
 */
@WebServlet(name = "TipoAvionServlet", urlPatterns = {"/TipoAvionServlet"})
public class TipoAvionServlet extends HttpServlet {

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
            String json;
           
            //Se crea el objeto
            TipoAvion tipoAvion = new TipoAvion();
            
            ServicioTipoAvion sta = new ServicioTipoAvion();
            
            Thread.sleep(1000);
            
            String accion = request.getParameter("accion");
            
            switch (accion) {                     
                case "insertar":
                    tipoAvion.setTipoAvion(parseInt(request.getParameter("tipoAvion"))); 
                    tipoAvion.setCapacidad(parseInt(request.getParameter("capacidad")));
                    tipoAvion.setAnno(request.getParameter("anno")); 
                    tipoAvion.setModelo(request.getParameter("modelo"));
                    tipoAvion.setMarca(request.getParameter("marca")); 
                    tipoAvion.setFilas(parseInt(request.getParameter("filas")));
                    tipoAvion.setAsientosPorFila(parseInt(request.getParameter("asientosPorFila"))); 
                
                    sta.insertartTipoAvion(tipoAvion);

                    out.print("C~El objeto fue ingresado correctamente");
                    break;
                    
                case "actualizar":
                    tipoAvion.setTipoAvion(parseInt(request.getParameter("tipoAvion"))); 
                    tipoAvion.setCapacidad(parseInt(request.getParameter("capacidad")));
                    tipoAvion.setAnno(request.getParameter("anno")); 
                    tipoAvion.setModelo(request.getParameter("modelo"));
                    tipoAvion.setMarca(request.getParameter("marca")); 
                    tipoAvion.setFilas(parseInt(request.getParameter("filas")));
                    tipoAvion.setAsientosPorFila(parseInt(request.getParameter("asientosPorFila"))); 
                
                    sta.actualizarTipoAvion(tipoAvion);

                    out.print("C~El objeto fue actualizado correctamente");
                    break;
                    
                case "buscar": 
                    tipoAvion = sta.buscarTipoAvion(parseInt(request.getParameter("tipoAvion")));
                    
                    //se pasa la informacion del objeto a formato JSON
                    json = new Gson().toJson(tipoAvion);
                    out.print(json);
                    break;
                    
                case "listar":                
                    List<TipoAvion> list = new ArrayList(sta.listarTipoAvion());
                    
                    json = new Gson().toJson(list);                    
                    out.print(json);
                    break;
                    
                default:
                    out.print("E~No se indicó la acción que se desea realizare");
                    break;
                    
            }
         
        } catch (Exception e) {
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
