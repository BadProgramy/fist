
	(function() {
        var dlgtrigger = document.querySelector('[data-dialog2]'),
            somedialog = document.getElementById(dlgtrigger.getAttribute('data-dialog2')),
            dlg = new DialogFx(somedialog);

        dlgtrigger.addEventListener('click', dlg.toggle.bind(dlg));

    })();
