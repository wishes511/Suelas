/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
select distinct a.cveagente,ag.nombre
from RACobranzaPhylon.dbo.abonos a join RCPTPhylon.dbo.DFacturas dp on substring(a.referenciac,0,8)=dp.factura 
 join RACobranzaPhylon.dbo.Agentes ag on a.cveagente=ag.cveagente
where saldo=0 and (dp.linea=23 or dp.linea = 24) and (convert(date,a.fecha) between $P{f1}  and $P{f2}) 
group by a.cveagente,ag.nombre
order by a.cveagente

 */
package suelas;

/**
 *
 * @author gateway1
 */
public class Suelas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
