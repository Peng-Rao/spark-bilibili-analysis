import axios from 'axios'

const instance = axios.create({
    baseURL: '/api',
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
})

export default {
    getOrders () {
        return instance.get('/orders')
            .then(res => res.data.data)
    }
}
