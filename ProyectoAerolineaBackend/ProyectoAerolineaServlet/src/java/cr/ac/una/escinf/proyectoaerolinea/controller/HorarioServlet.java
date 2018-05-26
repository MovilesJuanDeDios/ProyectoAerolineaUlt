/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.controller;

import com.google.gson.Gson;
import cr.ac.una.escinf.proyectoaerolinea.data.service.ServicioHorario;
import cr.ac.una.escinf.proyectoaerolinea.models.Horario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author slon
 */
public class HorarioServlet extends HttpServlet {

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
            Horario horario = new Horario();
                 
            ServicioHorario sh = new ServicioHorario();
            
            Thread.sleep(1000);
            
            String accion = request.getParameter("accion");
            
            switch (accion) {                     
                case "insertar":
                          horario.setHorario(Integer.parseInt(request.getParameter("horario")));
                          horario.setDiaSalida(request.getParameter("diaSalida"));
                          horario.setDiaLlegada(request.getParameter("diaLlegada"));
                          horario.setHoraSalida(request.getParameter("horaSalida"));
                          horario.setHoraLlegada(request.getParameter("horaLlegada"));
                          horario.setPrecio(Integer.parseInt(request.getParameter("precio")));
                          horario.setRuta(Integer.parseInt(request.getParameter("ruta")));
                          
                          sh.insertarHorario(horario);

                          out.print("C~El objeto fue ingresado correctamente");
                          break;

                case "actualizar":
                        horario.setHorario(Integer.parseInt(request.getParameter("horario")));
                        horario.setDiaSalida(request.getParameter("diaSalida"));
                        horario.setDiaLlegada(request.getParameter("diaLlegada"));
                        horario.setHoraSalida(request.getParameter("horaSalida"));
                        horario.setHoraLlegada(request.getParameter("horaLlegada"));
                        horario.setPrecio(Integer.parseInt(request.getParameter("precio")));
                        horario.setRuta(Integer.parseInt(request.getParameter("ruta")));
                          
                        sh.actualizarHorario(horario);

                        out.print("C~El objeto fue actualizado correctamente");
                        break;

                case "listar":

                    List<Horario> listHora = new ArrayList(sh.listarHorarios());
                    json = new Gson().toJson(listHora);   
                    out.print(json);
                    break;
                    
                case "buscar": 
                    horario = sh.buscarHorario(Integer.parseInt(request.getParameter("horario")));
                    
                    //se pasa la informacion del objeto a formato JSON
                    json = new Gson().toJson(horario);
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
