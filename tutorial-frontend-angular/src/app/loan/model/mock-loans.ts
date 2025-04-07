import { LoanPage } from "./LoanPage";

export const LOAN_DATA: LoanPage = {
    content: [
        { id: 1, game: { id: 1, title: 'Juego 1', age: 8, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' }}, client: { id: 1, name: 'Cliente 1' }, loanDate: new Date("2025-04-01"), returnDate: new Date("2025-04-02") },
        { id: 2, game: { id: 2, title: 'Juego 2', age: 10, category: { id: 2, name: 'Categoría 2' }, author: { id: 2, name: 'Autor 2', nationality: 'Nacionalidad 2' }}, client: { id: 2, name: 'Cliente 2' }, loanDate: new Date("2025-04-10"), returnDate: new Date("2025-04-14") },
        { id: 3, game: { id: 3, title: 'Juego 3', age: 5, category: { id: 3, name: 'Categoría 3' }, author: { id: 3, name: 'Autor 3', nationality: 'Nacionalidad 3' }}, client: { id: 3, name: 'Cliente 3' }, loanDate: new Date("2025-03-26"), returnDate: new Date("2025-04-01") },
        { id: 4, game: { id: 4, title: 'Juego 4', age: 13, category: { id: 4, name: 'Categoría 4' }, author: { id: 4, name: 'Autor 4', nationality: 'Nacionalidad 4' }}, client: { id: 4, name: 'Cliente 4' }, loanDate: new Date("2025-05-02"), returnDate: new Date("2025-05-13") },
        { id: 5, game: { id: 5, title: 'Juego 5', age: 4, category: { id: 5, name: 'Categoría 5' }, author: { id: 5, name: 'Autor 5', nationality: 'Nacionalidad 5' }}, client: { id: 5, name: 'Cliente 5' }, loanDate: new Date("2025-04-25"), returnDate: new Date("2025-05-03") },
        { id: 6, game: { id: 6, title: 'Juego 6', age: 10, category: { id: 6, name: 'Categoría 6' }, author: { id: 6, name: 'Autor 6', nationality: 'Nacionalidad 6' }}, client: { id: 6, name: 'Cliente 6' }, loanDate: new Date("2025-04-01"), returnDate: new Date("2025-04-10") },
        { id: 7, game: { id: 7, title: 'Juego 7', age: 16, category: { id: 7, name: 'Categoría 7' }, author: { id: 7, name: 'Autor 7', nationality: 'Nacionalidad 7' }}, client: { id: 7, name: 'Cliente 7' }, loanDate: new Date("2025-04-07"), returnDate: new Date("2025-04-25") },
    ],
    pageable: {
        pageSize: 5,
        pageNumber: 0,
        sort: [{ property: 'id', direction: 'ASC' }],
    },
    totalElements: 7,
};