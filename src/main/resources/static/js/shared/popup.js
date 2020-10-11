

var modalContainer = document.createElement('div');
    modalContainer.setAttribute('id', 'modal'); 



function modalShow(message) {

    var customBox = document.createElement('div');
    customBox.className = 'custom-box';

    customBox.innerHTML = '<p>'+message+'</p>';
    customBox.innerHTML += '<button id="modal-close">OK</button>';


    modalContainer.appendChild(customBox);
    document.body.appendChild(modalContainer);

    document.getElementById('modal-close').addEventListener('click', function() {
        modalClose();
    });

    if (document.getElementById('modal-confirm')) {
        document.getElementById('modal-confirm').addEventListener('click', function () {
           console.log('Confirmé !');
           modalClose();
        });
    } else if (document.getElementById('modal-submit')) {
        document.getElementById('modal-submit').addEventListener('click', function () {
            console.log(document.getElementById('modal-prompt').value);
            modalClose();
        });
    }
}

function modalClose() {
    while (modalContainer.hasChildNodes()) {
        modalContainer.removeChild(modalContainer.firstChild);
    }
    document.body.removeChild(modalContainer);
}



function getUrlValue()
{
    //load json file
    var jsonData = (function () {
        var json = null;
        $.ajax({
            'async': false,
            'global': false,
            'url': "js/config.json",
            'dataType': "json",
            'success': function (data) {
                jsonData = data;
            }
        });
        return jsonData;
    })()

    return jsonData['config'][0].urlValue;
}

function pageRedirect() {
	window.location.replace("Confirmation_d_inscription.html");
}