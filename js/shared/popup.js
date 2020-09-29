

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