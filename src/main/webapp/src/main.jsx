/**
 * @fileoverview Punto de entrada principal para la aplicación.
 * @author Fatima Navarro
 * 
 * Este archivo es responsable de renderizar el componente principal de la aplicación, App.jsx,
 * en el elemento con el id 'root' en el index.html.
 * 
 * @requires react
 * @requires react-dom/client
 * @requires ./App
 * @requires ./index.css
 */

import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)