class Lord {
    constructor(id, name, age, planets) {
        this.id         = id;
        this.name       = name;
        this.age        = age;
        this.planets    = planets;
    }
}


function showLordsPage(page) {
    preparePageToFillIn();
    currentPage = page;
    if (currentSection !== 0) {
        currentSection = 0;
        clearAdditionalActions();
        showLordsAdditionalActions();
    }
    $.ajax({
        url: SERVER_URL + "/lords?page=" + page + "&size=" + pageSize,
        method: 'GET',
        headers: {
            'Content-type':'application/json;charset=urf-8'
        }
    })
        .done(lordsPage => {
            fillInLordsContentTable(lordsPage);
            showPagination(showLordsPage, lordsPage.totalPages);
        })
        .fail(err => {
            switch (err.status) {
                case 0:
                    showMessage('Connection refused', 0);
                    break;
                case 404:
                    fillInLordsContentTableHeader();
                    appendCreateLordLink();
                    showMessage('Lords not found');
                    break;
            }
        });
}


function showYoungestLords(page) {
    preparePageToFillIn();
    currentPage = page;
    $.ajax({
        url: SERVER_URL + "/lords/youngest?page=" + page + "&size=" + pageSize,
        method: 'GET',
        headers: {
            'Content-type':'application/json;charset=urf-8'
        }
    })
        .done(lordsPage => {
            fillInLordsContentTable(lordsPage);
        })
        .fail(err => {
            switch (err.status) {
                case 0:
                    showMessage('Connection refused', 0);
                    break;
                case 404:
                    fillInLordsContentTableHeader();
                    appendCreateLordLink();
                    showMessage('Lords not found');
                    break;
            }
        });
}


function showLordsWithoutPlanets(page) {
    preparePageToFillIn();
    currentPage = page;
    $.ajax({
        url: SERVER_URL + "/lords/without_planets?page=" + page + "&size=" + pageSize,
        method: 'GET',
        headers: {
            'Content-type':'application/json;charset=urf-8'
        }
    })
        .done(lordsPage => {
            fillInLordsContentTable(lordsPage);
            showPagination(showLordsWithoutPlanets, lordsPage.totalPages);
        })
        .fail(err => {
            switch (err.status) {
                case 0:
                    showMessage('Connection refused', 0);
                    break;
                case 404:
                    fillInLordsContentTableHeader();
                    appendCreateLordLink();
                    showMessage('Lords not found');
                    break;
            }
        });
}


function createLord(lord) {
    clearMessage();
    $.ajax({
        url: SERVER_URL + "/lords",
        method: 'POST',
        headers: {
            'Content-type':'application/json;charset=utf-8'
        },
        data: JSON.stringify(lord)
    })
        .done(lord => {
            fillInLordRow(lord);
            hideCreateLordForm();
            showMessage(`Lord '${lord.name}' has been successfully created`, 1)
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


function fillInLordsContentTable(lordsPage) {
    fillInLordsContentTableHeader();
    appendCreateLordLink();
    fillInLordsContent(lordsPage);
}


function fillInLordsContentTableHeader() {
    content.html('<tr><th class="id">id</th><th class="name">name</th><th class="age">age</th><th class="planets">planets</th><th class="actions">actions</th></tr>');
}


function appendCreateLordLink() {
    content.append('<tr id="create_lord_row"><td colspan="4"></td><td class="actions"><a id="create_lord_link" href="#">create lord</a></td></tr>');
    $('#create_lord_link').click(function () {
        showCreateLordForm();
    });
}


function fillInLordsContent(lordsPage) {
    lordsPage.content.forEach(lord => {
        fillInLordRow(lord);
    });
}


function fillInLordRow(lord) {
    content.append(`<tr><td id="id_${lord.id}" class="id">${lord.id}</td><td id="name_${lord.id}" class="name">${lord.name}</td><td id="age_${lord.id}" class="age">${lord.age}</td><td id="planets_${lord.id}" class="planets">${getLordPlanetsAsUl(lord.planets)}</td><td id="actions_${lord.id}" class="actions"></td></tr>`);
}


function getLordPlanetsAsUl(planets) {
    if (planets) {
        return `<ul>${planets.map(planet => getLordPlanetListItem(planet)).join('')}</ul>`;
    } else {
        return '';
    }
}


function getLordPlanetListItem(planet) {
    return `<li><span hidden>${planet.id}</span><span>${planet.name}</span></li>`;
}


function showCreateLordForm() {
    $('#create_lord_row').html(`<td></td><td id="created_name" class="name"><input type="text" name="created_lord_name"></td><td id="created_age" class="age"><input type="number" name="created_lord_age"></td><td></td><td id="create_actions" class="actions"><a id="apply_create_lord" href="#">create</a> <a id="cancel_create_lord" href="#">cancel</a></td>`);
    $('#apply_create_lord').click(function () {
        createLord(new Lord(null, $('input[name="created_lord_name"]').val(), $('input[name="created_lord_age"]').val(), null));
    });
    $('#cancel_create_lord').click(function () {
        hideCreateLordForm();
        clearMessage();
    });
}


function hideCreateLordForm() {
    $('#create_lord_row').html(`<td colspan="4"></td><td class="actions"><a id="create_lord_link" href="#">create lord</a></td>`);
    $('#create_lord_link').click(function () {
        showCreateLordForm();
    });
}


function showLordsAdditionalActions() {
    additional_actions.html('<ul><li><a id="all_lords" href="#">All lords</a></li><li><a id="youngest_lords" href="#">Top 10 youngest lords</a></li><li><a id="lords_without_parents" href="#">Lords without planets</a></li></ul>');
    $('#all_lords').click(function () {
        showLordsPage(0);
    });
    $('#youngest_lords').click(function () {
        showYoungestLords(0);
    });
    $('#lords_without_parents').click(function () {
        showLordsWithoutPlanets(0);
    });
}