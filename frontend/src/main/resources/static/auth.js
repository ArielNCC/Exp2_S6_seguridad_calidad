(function () {
    async function apiFetch(url, options) {
        const fetchOptions = options || {};
        const skipAuthRedirect = Boolean(fetchOptions.skipAuthRedirect);
        const requestOptions = {
            ...fetchOptions,
            credentials: 'same-origin'
        };

        delete requestOptions.skipAuthRedirect;

        const response = await fetch(url, requestOptions);
        if ((response.status === 401 || response.status === 403) && !skipAuthRedirect) {
            await fetch('/api/auth/logout', {
                method: 'POST',
                credentials: 'same-origin'
            }).catch(() => undefined);
            window.location.href = '/login';
            throw new Error('Sesion expirada');
        }

        return response;
    }

    async function hasAuthSession() {
        const response = await apiFetch('/api/auth/session', {
            cache: 'no-store',
            skipAuthRedirect: true
        });
        return response.ok;
    }

    async function logout() {
        try {
            await apiFetch('/api/auth/logout', {
                method: 'POST',
                skipAuthRedirect: true
            });
        } finally {
            window.location.href = '/login';
        }
    }

    window.apiFetch = apiFetch;
    window.hasAuthSession = hasAuthSession;
    window.logout = logout;
})();