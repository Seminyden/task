function showNavigation() {
    navigation.html(`<ul><li><a id="lords-nav-link" href="#">Lords</a></li><li><a id="planets-nav-link" href="#">Planets</a></li></ul>`);
    $('#lords-nav-link').click(function () {
        showLordsPage(0);
    });
    $('#planets-nav-link').click(function () {
        showPlanetsPage(0)
    });
}


function showStartPage() {
    showLordsPage(0);
}


function preparePageToFillIn() {
    clearMessage();
    clearContentTable();
    clearPagination();
}


function showMessage(mess, status) {
    switch (status) {
        case 0:
            mess = '<span class="error">' + mess + '</span>';
            break;
        case 1:
            mess = '<span class="success">' + mess + '</span>';
            break;
    }
    message.html(mess);
}


function clearMessage() {
    message.html('&nbsp;');
}


function clearContentTable() {
    content.empty();
}


function clearAdditionalActions() {
    additional_actions.empty();
}


function showPagination(callback, totalPages) {
    if (totalPages > 1) {
        for (let i = 0; i < totalPages; i++) {
            if (i === currentPage) {
                pagination.append(`<li><a class="current_page" href="#">${i + 1}</a></li>`);
            } else {
                pagination.append(`<li><a id="page_${i}" href="#">${i + 1}</a></li>`);
                $(`#page_${i}`).click(function () {
                    callback(i);
                })
            }
        }
    }
}


function clearPagination() {
    pagination.empty();
}