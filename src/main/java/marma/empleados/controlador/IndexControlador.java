package marma.empleados.controlador;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Locked;
import marma.empleados.modelo.Empleado;
import marma.empleados.servicio.EmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexControlador {
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    EmpleadoServicio empleadoServicio;

    @RequestMapping(value = "/", method = RequestMethod.GET)
     public String iniciar(ModelMap modelo){
        List<Empleado> empleados = empleadoServicio.listarEmpleados();
        empleados.forEach(empleado -> logger.info(empleado.toString()));
        modelo.put("empleados", empleados);
        return "index"; //index.jsp
     }

    @RequestMapping(value = "/agregar", method = RequestMethod.GET)
    public String agregar(){
        return "agregar"; //agregar.jsp
    }
    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public String agregar(@ModelAttribute("empleadoForma") Empleado empleado){
        logger.info("Empleado a agregar: " +empleado);
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/"; //redirige al path "/"
    }
    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public String mostrarEditar(@RequestParam int idEmpleado, ModelMap modelo){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(idEmpleado);
        logger.info("Empleado a editar: " +empleado);
        return "editar"; // mostrar editar .jsp
    }
    @RequestMapping(value = "/editar", method = RequestMethod.POST)
    public String editar(@ModelAttribute("empleadoForma") Empleado empleado){
        logger.info("Empleado a guardar (editar): " +empleado);
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/"; //redirigimos al controlador "/"
    }
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public String eliminar(@RequestParam int idEmpleado) {
        Empleado empleado = new Empleado();
        empleadoServicio.eliminarEmpleado(empleado);
        return "redirect:/";
    }
}
