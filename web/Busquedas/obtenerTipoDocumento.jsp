<%@page import="Entidades.Tipodocumento"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.TipodocumentoJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("tipoDocumento"));

    TipodocumentoJpaController ses = new TipodocumentoJpaController();
    List lista = ses.findTipodocumentoEntities();

    for (int i = 0; i < lista.size(); i++) {
        Tipodocumento tipo2 = (Tipodocumento) lista.get(i);

        if (tipo2.getIdTipoDocumento() == id) {

            out.print("<option value='" + tipo2.getIdTipoDocumento() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdTipoDocumento() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>