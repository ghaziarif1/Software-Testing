const request = require('supertest');
const app = require('../app'); // Assure-toi que c'est le chemin correct vers app.js

describe('POST /api/users', () => {
    it('should add a new user and return it with an id', async () => {
        const newUser = { name: 'Charlie', email: 'charlie@example.com' };

        const response = await request(app)
            .post('/api/users')
            .send(newUser)
            .set('Accept', 'application/json');

        // Vérifie que le statut est 201 (Created)
        if (response.status !== 201) {
            throw new Error(`Expected status 201, got ${response.status}`);
        }

        // Vérifie que la réponse contient le nom, l'email et un id
        const body = response.body;
        if (body.name !== newUser.name || body.email !== newUser.email || !body.id) {
            throw new Error('Response body is not as expected: ' + JSON.stringify(body));
        }
    });
});