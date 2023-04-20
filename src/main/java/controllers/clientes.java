package controllers;

import interfaces.Operations;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.ClienteEntity;
import services.clienteServiceImp;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "clientes", urlPatterns = {"/clientes", "/clientes/create", "/clientes/update", "/clientes/delete", "/clientes/save", "/clientes/edit" , "/clientes/destroy"})
public class clientes extends HttpServlet {

    clienteServiceImp clienteService = new clienteServiceImp();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();
        switch (action) {
            case "/clientes/create":
                showNewForm(request, response);
                break;
            case "/clientes/edit":
                showEditForm(request, response);
                break;
            case "/clientes/delete":
                deleteCliente(request, response);
                break;
            default:
                listClientes(request, response);
                break;
        }

    }

    private void listClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = "views/cliente/ClienteList.jsp";
        List<ClienteEntity> clientes = null;
        try {
            clientes = clienteService.listar();
        } catch (Exception e) {
            String error = "No se pudo obtener la lista de clientes";
            request.setAttribute("error", error);
            request.getRequestDispatcher(view).forward(request, response);
        }
        request.setAttribute("clientes", clientes);
        try {
            request.getRequestDispatcher(view).forward(request, response);
        } catch (ServletException e) {
            sendErrorToHome(request, response);
        } catch (IOException e) {
            sendErrorToHome(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String view = "../views/cliente/ClienteForm.jsp";
        try {
            request.getRequestDispatcher(view).forward(request, response);
        } catch (ServletException e) {
            sendErrorToHome(request, response);
        } catch (IOException e) {
            sendErrorToHome(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String view = "../views/cliente/ClienteUpdate.jsp";
        searchCliente(request, response, view);
    }

    private void deleteCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String view = "../views/cliente/ClienteDelete.jsp";
        searchCliente(request, response, view);
    }

    private void searchCliente(HttpServletRequest request, HttpServletResponse response, String view) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ClienteEntity cliente = null;
        try {
            cliente = (ClienteEntity) clienteService.buscar(id);
        } catch (Exception e) {
            String error = "No se pudo obtener el cliente";
            request.setAttribute("error", error);
            try {
                request.getRequestDispatcher(view).forward(request, response);
            } catch (ServletException e1) {
                sendErrorToHome(request, response);
            } catch (IOException e1) {
                sendErrorToHome(request, response);
            }
        }
        request.setAttribute("cliente", cliente);
        try {
            request.getRequestDispatcher(view).forward(request, response);
        } catch (ServletException e) {
            sendErrorToHome(request, response);
        } catch (IOException e) {
            sendErrorToHome(request, response);
        }
    }

    private void sendErrorToHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "Ha ocurrido un error inesperado";
        request.setAttribute("error", error);
        response.sendRedirect(request.getContextPath() + "/");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();
        switch (action) {
            case "/clientes/save":
                saveCliente(request, response);
                break;
            case "/clientes/update":
                updateCliente(request, response);
                break;
            case "/clientes/destroy":
                destroyCliente(request, response);
                break;
            default:
                listClientes(request, response);
                break;
        }
    }

    private void saveCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String view = "../views/cliente/ClienteList.jsp";
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setDireccion(request.getParameter("direccion"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setEmail(request.getParameter("email"));
        try {
            clienteService.insertar(cliente);
        } catch (Exception e) {
            String error = "No se pudo guardar el cliente";
            request.setAttribute("error", error);
            try {
                request.getRequestDispatcher(view).forward(request, response);
            } catch (ServletException ex) {
                sendErrorToHome(request, response);
            } catch (IOException ex) {
                sendErrorToHome(request, response);
            }
        }
        try {
            response.sendRedirect(request.getContextPath() + "/clientes");
        } catch (IOException e) {
            sendErrorToHome(request, response);
        }
    }

    private void updateCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String view = "../views/cliente/ClienteList.jsp";
        ClienteEntity cliente = null;
        try {
            //Data to update
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");

            //Upadate data
            cliente = (ClienteEntity) clienteService.buscar(id);
            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);

            clienteService.actualizar(cliente);
        } catch (Exception e) {
            String error = "No se pudo actualizar el cliente";
            request.setAttribute("error", error);
            try {
                request.getRequestDispatcher(view).forward(request, response);
            } catch (ServletException ex) {
                sendErrorToHome(request, response);
            } catch (IOException ex) {
                sendErrorToHome(request, response);
            }
        }
        try {
            response.sendRedirect(request.getContextPath() + "/clientes");
        } catch (IOException e) {
            sendErrorToHome(request, response);
        }
    }

    private void destroyCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String view = "../views/cliente/ClienteList.jsp";
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            clienteService.eliminar(id);
        } catch (Exception e) {
            String error = "No se pudo eliminar el cliente";
            request.setAttribute("error", error);
            try {
                request.getRequestDispatcher(view).forward(request, response);
            } catch (ServletException ex) {
                sendErrorToHome(request, response);
            } catch (IOException ex) {
                sendErrorToHome(request, response);
            }
        }
        try {
            response.sendRedirect(request.getContextPath() + "/clientes");
        } catch (IOException e) {
            sendErrorToHome(request, response);
        }
    }

    public void destroy() {
    }
}