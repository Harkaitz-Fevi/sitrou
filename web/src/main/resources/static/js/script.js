
//---------------------------------------------BILATZAILEAK-------------------------------------------------------------------
document.addEventListener("DOMContentLoaded", function () {
   
    const inputGailua = document.getElementById("bilatuGailua");
    const inputErabiltzailea = document.getElementById("bilatuErabiltzailea");
    const taulaGorputza = document.getElementById("taulaGorputza");

    // Elementuak paginan existitzen badira, eventuak gehitzen ditugu.
    if (inputGailua && inputErabiltzailea && taulaGorputza) {
        
        // Input-a kontuan hartzen dugu, erabiltzaileak klikatzen duen bakoitzean
        inputGailua.addEventListener("input", iragaziTaula);
        inputErabiltzailea.addEventListener("input", iragaziTaula);

        function iragaziTaula() {
            // Pasatzen dugu dena letra xehetara, arazoak ez izateko bilatzerako orduan
            const testuaGailua = inputGailua.value.toLowerCase();
            const testuaErabiltzailea = inputErabiltzailea.value.toLowerCase();
            
            // Tr guztiak hartzen ditugu.
            const ilarak = taulaGorputza.getElementsByTagName("tr");

            for (let i = 0; i < ilarak.length; i++) {
                // Fila aktualaren karratuak hartzen ditugu
                // index 0 da 'Id gailua' (th), index 1 da 'Id erabiltzailea' (td)
                const gelaxkaGailua = ilarak[i].getElementsByTagName("th")[0]; 
                const gelaxkaErabiltzailea = ilarak[i].getElementsByTagName("td")[0];

                if (gelaxkaGailua && gelaxkaErabiltzailea) {
                    const balioaGailua = gelaxkaGailua.textContent || gelaxkaGailua.innerText;
                    const balioaErabiltzailea = gelaxkaErabiltzailea.textContent || gelaxkaErabiltzailea.innerText;

                    // Konprobatzen dugu ea karratuaren testua bilatzen duguna daukan
                    const batDatorGailua = balioaGailua.toLowerCase().includes(testuaGailua);
                    const batDatorErabiltzailea = balioaErabiltzailea.toLowerCase().includes(testuaErabiltzailea);

               // Bi filtroekin koinziditzen badu (edo filtroak hutsik balidn badadude), fila bistaratzen dugu
                    if (batDatorGailua && batDatorErabiltzailea) {
                        ilarak[i].style.display = ""; // Fila erakusten du 
                    } else {
                        ilarak[i].style.display = "none"; // Fila eskutatzen du
                    }
                }
            }
        }
    }
});
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
