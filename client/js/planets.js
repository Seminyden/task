class Planet {
    constructor(id, name, lord) {
        this.id     = id;
        this.name   = name;
        this.lord   = lord;
    }
}


function showPlanetsPage(page) {
    preparePageToFillIn();
    currentPage = page;
    if (currentSection !== 1) {
        currentSection = 1;
        clearAdditionalActions();
    }
    $.ajax({
        url: SERVER_URL + "/planets?page=" + page + "&size" + pageSize,
        method: 'GET',
        headers: {
            'Content-type':'application/json;charset=utf-8'
        }
    })
        .done(planetsPage => {
            fillInPlanetsContentTable(planetsPage);
            showPagination(showPlanetsPage, planetsPage.totalPages);
        })
        .fail(err => {
            switch (err.status) {
                case 0:
                    showMessage('Connection refused', 0);
                    break;
                case 404:
                    fillInPlanetsContentTableHeader();
                    appendCreatePlanetLink();
                    showMessage('Planets not found');
                    break;
            }
        })
}


function bindLordToPlanet(planetId, lord) {
    clearMessage();
    let planet = new Planet(null, null, lord);
    $.ajax({
        url: SERVER_URL + "/planets/" + planetId,
        method: 'PUT',
        headers: {
            'Content-type':'application/json;charset=utf-8'
        },
        data: JSON.stringify(planet)
    })
        .done(planet => {
            fillInPlanetLord(planet);
            showMessage(`Lord '${planet.lord.name}' has been successfully bind to planet '${planet.name}'`, 1);
        })
        .fail(err => {
            switch (err.status) {
                case 0:
                    showMessage('Connection refused', 0);
                    break;
                case 400:
                    showMessage(err.responseText, 0);
                    break;
            }
        })
}


function deletePlanet(planetId) {
    clearMessage();
    $.ajax({
        url: SERVER_URL + '/planets/' + planetId,
        method: 'DELETE',
        headers: {
            'Content-type':'application/json:charset=urf-8'
        }
    })
        .done(planet => {
            removePlanetRow(planet);
        })
        .fail(err => {
            switch (err.status) {
                case 0:
                    showMessage('Connection refused', 0);
                    break;
                case 404:
                    showMessage('Planet not found');
                    break;
            }
        })
}


function createPlanet(planet) {
    clearMessage();
    $.ajax({
        url: SERVER_URL + '/planets',
        method: 'POST',
        headers: {
            'Content-type':'application/json;charset=utf-8'
        },
        data: JSON.stringify(planet)
    })
        .done(planet => {
            fillInPlanetRow(planet);
            hideCreatePlanetForm();
            showMessage(`Planet '${planet.name}' has been successfully created`, 1)
        })
        .fail(err => {
            switch (err.status) {
                case 0:
                    showMessage('Connection refused', 0);
                    break;
                case 400:
                    showMessage(err.responseText, 0);
                    break;
            }
        })
}


function fillInPlanetsContentTable(planetsPage) {
    fillInPlanetsContentTableHeader();
    appendCreatePlanetLink();
    fillInPlanetsContent(planetsPage);
}


function fillInPlanetsContentTableHeader() {
    content.html('<tr><th class="id">id</th><th class="name">name</th><th class="lord">lord</th><th class="actions">actions</th></tr>');
}


function appendCreatePlanetLink() {
    content.append('<tr id="create_planet_row"><td colspan="3"></td><td class="actions"><a id="create_planet_link" href="#">create planet</a></td></tr>');
    $('#create_planet_link').click(function () {
        clearMessage();
        showCreatePlanetForm();
    });
}


function fillInPlanetsContent(planetsPage) {
    planetsPage.content.forEach(planet => fillInPlanetRow(planet));
}


function fillInPlanetRow(planet) {
    content.append(`<tr id="planet_${planet.id}"><td id="id_${planet.id}" class="id">${planet.id}</td><td id="name_${planet.id}" class="name">${planet.name}</td><td id="lord_${planet.id}" class="lord">${getPlanetLord(planet)}</td><td id="actions_${planet.id}" class="actions"><a id="delete_planet_${planet.id}" href="#">delete</a></td></tr>`);
    $(`#bind_lord_${planet.id}`).click(function () {
        clearMessage();
        showBindLordForm(planet.id);
    });
    $(`#delete_planet_${planet.id}`).click(function () {
        clearMessage();
        deletePlanet(planet.id);
    });
}


function getPlanetLord(planet) {
    let lord = planet.lord;
    if (lord) {
        return `<span hidden>${lord.id}</span><span>${lord.name}</span>`
    }
    return `<a id="bind_lord_${planet.id}" href="#">+</a>`;
}


function showBindLordForm(planetId) {
    $(`#lord_${planetId}`).html(`<label>Lord id:</label> <input type="number" name="planet_${planetId}_lord_id">`);
    $(`#actions_${planetId}`).html(`<a id="apply_${planetId}" href="#">apply</a> <a id="cancel_${planetId}" href="#">cancel</a>`);
    $(`#apply_${planetId}`).click(function () {
        clearMessage();
        bindLordToPlanet(planetId, new Lord($(`input[name='planet_${planetId}_lord_id']`).val(), null, null, null));
    });
    $(`#cancel_${planetId}`).click(function () {
        clearMessage();
        hideBindLordForm(planetId);
    });
}


function hideBindLordForm(planetId) {
    $(`#lord_${planetId}`).html(`<a id="bind_lord_${planetId}" href="#">+</a>`);
    $(`#actions_${planetId}`).html(`<a id="delete_planet_${planetId}" href="#">delete</a>`);
    $(`#bind_lord_${planetId}`).click(function () {
        clearMessage();
        showBindLordForm(planetId);
    });
    $(`#delete_planet_${planetId}`).click(function () {
        clearMessage();
        deletePlanet(planetId);
    });
}


function fillInPlanetLord(planet) {
    hideBindLordForm(planet.id);
    $(`#lord_${planet.id}`).html(getPlanetLord(planet));
}


function removePlanetRow(planet) {
    showMessage(`Planet '${planet.name}' has been successfully deleted`, 1);
    $(`#planet_${planet.id}`).remove();
}


function showCreatePlanetForm() {
    $('#create_planet_row').html('<td></td><td id="created_name" class="name"><input type="text" name="created_planet_name"></td><td></td><td id="create_actions" class="actions"><a id="apply_create_planet" href="#">create</a> <a id="cancel_create_planet" href="#">cancel</a></td>');
    $('#apply_create_planet').click(function () {
        clearMessage();
        createPlanet(new Planet(null, $('input[name="created_planet_name"]').val()), null);
    });
    $('#cancel_create_planet').click(function () {
        clearMessage();
        hideCreatePlanetForm();
    });
}


function hideCreatePlanetForm() {
    $('#create_planet_row').html('<td colspan="3"></td><td class="actions"><a id="create_planet_link" href="#">create planet</a></td>');
    $('#create_planet_link').click(function () {
        clearMessage();
        showCreatePlanetForm();
    });
}