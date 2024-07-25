/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Controladores.UsuarioJpaController;
import Controladores.VotanteJpaController;
import Controladores.exceptions.NonexistentEntityException;
import com.itextpdf.text.log.Logger;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Peralta
 */
public class IncriptacionContraseñas {

    UsuarioJpaController controlVotantes = new UsuarioJpaController();

    public static void main(String[] args) {
        IncriptacionContraseñas as = new IncriptacionContraseñas();
        as.Asiganacion();
    }

    public void Asiganacion() {
        List<Usuario> listaVotantes = controlVotantes.findUsuarioEntities();
        for (Usuario listaVotante : listaVotantes) {
            String id = String.valueOf(listaVotante.getCodigo());
            String clave = listaVotante.EncryptarClave(id);
            listaVotante.setClave(clave);
            try {
                controlVotantes.edit(listaVotante);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(IncriptacionContraseñas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
