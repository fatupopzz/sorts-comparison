import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

// Regresión para Insertion Sort - O(n^2)
function calculateInsertionRegression(data) {
  return calculatePolynomialRegression(data, 'insertionSort', 2);
}

// Regresión para Selection Sort - O(n^2)
function calculateSelectionRegression(data) {
  return calculatePolynomialRegression(data, 'selectionSort', 2);
}

// Regresión para Radix Sort - O(n)
function calculateRadixRegression(data) {
  return calculateLinearRegression(data, 'radixSort');
}

// Regresión polinomial
function calculatePolynomialRegression(data, key, degree) {
  const points = [];
  const minX = Math.min(...data.map(item => item.size));
  const maxX = Math.max(...data.map(item => item.size));
  
  // Coeficientes usando mínimos cuadrados
  const coefficients = polyfit(
    data.map(item => item.size),
    data.map(item => item[key]),
    degree
  );
  
  // Generar puntos
  for (let i = 0; i <= 50; i++) {
    const x = minX + (maxX - minX) * (i / 50);
    let y = 0;
    for (let j = 0; j <= degree; j++) {
      y += coefficients[j] * Math.pow(x, j);
    }
    points.push({
      size: x,
      [`${key}Trend`]: y
    });
  }
  
  return points;
}

// Regresión lineal
function calculateLinearRegression(data, key) {
  const n = data.length;
  let sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
  
  data.forEach(item => {
    const x = item.size;
    const y = item[key];
    sumX += x;
    sumY += y;
    sumXY += x * y;
    sumXX += x * x;
  });
  
  const slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
  const intercept = (sumY - slope * sumX) / n;
  
  const points = [];
  const minX = Math.min(...data.map(item => item.size));
  const maxX = Math.max(...data.map(item => item.size));
  
  for (let i = 0; i <= 50; i++) {
    const x = minX + (maxX - minX) * (i / 50);
    points.push({
      size: x,
      [`${key}Trend`]: slope * x + intercept
    });
  }
  
  return points;
}

// Función auxiliar para regresión polinomial
function polyfit(x, y, degree) {
  const n = x.length;
  let matrix = [];
  let vector = [];
  
  for (let i = 0; i <= degree; i++) {
    matrix[i] = [];
    for (let j = 0; j <= degree; j++) {
      let sum = 0;
      for (let k = 0; k < n; k++) {
        sum += Math.pow(x[k], i + j);
      }
      matrix[i][j] = sum;
    }
    
    let sum = 0;
    for (let k = 0; k < n; k++) {
      sum += y[k] * Math.pow(x[k], i);
    }
    vector[i] = sum;
  }
  
  return gaussianElimination(matrix, vector);
}

// Eliminación Gaussiana
function gaussianElimination(matrix, vector) {
  const n = vector.length;
  for (let i = 0; i < n; i++) {
    let maxEl = Math.abs(matrix[i][i]);
    let maxRow = i;
    for (let k = i + 1; k < n; k++) {
      if (Math.abs(matrix[k][i]) > maxEl) {
        maxEl = Math.abs(matrix[k][i]);
        maxRow = k;
      }
    }

    for (let k = i; k < n; k++) {
      let tmp = matrix[maxRow][k];
      matrix[maxRow][k] = matrix[i][k];
      matrix[i][k] = tmp;
    }
    let tmp = vector[maxRow];
    vector[maxRow] = vector[i];
    vector[i] = tmp;

    for (let k = i + 1; k < n; k++) {
      const c = -matrix[k][i] / matrix[i][i];
      for (let j = i; j < n; j++) {
        if (i === j) {
          matrix[k][j] = 0;
        } else {
          matrix[k][j] += c * matrix[i][j];
        }
      }
      vector[k] += c * vector[i];
    }
  }

  const solution = new Array(n);
  for (let i = n - 1; i >= 0; i--) {
    solution[i] = vector[i];
    for (let j = i + 1; j < n; j++) {
      solution[i] -= matrix[i][j] * solution[j];
    }
    solution[i] /= matrix[i][i];
  }

  return solution;
}

function App() {
  const [data, setData] = useState([]);
  const [trendlines, setTrendlines] = useState({
    insertion: [],
    radix: [],
    selection: []
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showTrendlines, setShowTrendlines] = useState(true);
  const [showData, setShowData] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      try {
        const response = await fetch('/sorting_data.json');
        if (!response.ok) throw new Error('No se pudo cargar los datos');
        const jsonData = await response.json();
        
        const transformedData = jsonData
          .map(item => ({
            size: item.size,
            insertionSort: item.insertionTime / 1000000,
            radixSort: item.radixTime / 1000000,
            selectionSort: item.selectionTime / 1000000
          }))
          .sort((a, b) => a.size - b.size);
        
        setData(transformedData);

        setTrendlines({
          insertion: calculateInsertionRegression(transformedData),
          radix: calculateRadixRegression(transformedData),
          selection: calculateSelectionRegression(transformedData)
        });

        setLoading(false);
      } catch (err) {
        console.error('Error:', err);
        setError(err.message);
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const buttonStyle = {
    padding: '10px 20px',
    margin: '0 10px',
    borderRadius: '15px',
    cursor: 'pointer',
    fontSize: '1rem',
    transition: 'all 0.3s ease',
    background: 'linear-gradient(to bottom, #E3F2FD, #BBDEFB)',
    border: '2px solid rgba(255, 255, 255, 0.8)',
    color: '#1976D2',
    boxShadow: '0 2px 5px rgba(31, 38, 135, 0.2)'
  };

  const activeButtonStyle = {
    ...buttonStyle,
    background: 'linear-gradient(to bottom, #2196F3, #1976D2)',
    color: 'white'
  };

  if (loading || error) {
    return (
      <div style={{
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #E3F2FD 0%, #BBDEFB 100%)',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: '2rem'
      }}>
        <div style={{
          background: 'rgba(255, 255, 255, 0.4)',
          backdropFilter: 'blur(10px)',
          padding: '20px',
          borderRadius: '25px',
          border: '2px solid rgba(255, 255, 255, 0.8)',
          boxShadow: '0 4px 15px rgba(31, 38, 135, 0.2)'
        }}>
          <p style={{ 
            color: error ? '#f44336' : '#2196F3', 
            textAlign: 'center',
            margin: 0,
            fontSize: '1.2rem' 
          }}>
            {error ? `Error: ${error}` : 'Cargando datos...'}
          </p>
        </div>
      </div>
    );
  }

  return (
    <div style={{
      minHeight: '100vh',
      background: 'linear-gradient(135deg, #E3F2FD 0%, #BBDEFB 100%)',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      padding: '2rem',
      margin: 0,
      position: 'absolute',
      width: '100%',
      top: 0,
      left: 0,
      boxSizing: 'border-box'
    }}>
      <div style={{
        width: '100%',
        maxWidth: '1200px',
        display: 'flex',
        flexDirection: 'column',
        gap: '20px'
      }}>
        <div style={{
          background: 'linear-gradient(to bottom, #4CAF50, #388E3C)',
          borderRadius: '25px',
          padding: '20px',
          marginBottom: '20px',
          boxShadow: '0 4px 15px rgba(76, 175, 80, 0.3)',
          border: '2px solid rgba(255, 255, 255, 0.9)',
          backdropFilter: 'blur(10px)'
        }}>
          <h1 style={{
            fontSize: '2.5rem',
            fontWeight: '300',
            color: 'white',
            textAlign: 'center',
            margin: 0,
            textShadow: '2px 2px 4px rgba(0,0,0,0.2)'
          }}>Algoritmos de Ordenamiento</h1>
          <p style={{
            color: 'rgba(255,255,255,0.9)',
            textAlign: 'center',
            marginTop: '0.5rem',
            fontSize: '1.1rem'
          }}>Comparación de Rendimiento con Líneas de Tendencia</p>
        </div>

        <div style={{
          display: 'flex',
          justifyContent: 'center',
          gap: '10px',
          marginBottom: '20px'
        }}>
          <button 
            style={showData ? activeButtonStyle : buttonStyle}
            onClick={() => setShowData(!showData)}
          >
            {showData ? '✓ Datos Reales' : 'Datos Reales'}
          </button>
          <button 
            style={showTrendlines ? activeButtonStyle : buttonStyle}
            onClick={() => setShowTrendlines(!showTrendlines)}
          >
            {showTrendlines ? '✓ Líneas de Tendencia' : 'Líneas de Tendencia'}
          </button>
        </div>

        <div style={{
          background: 'rgba(255, 255, 255, 0.4)',
          borderRadius: '25px',
          padding: '20px',
          boxShadow: '0 4px 15px rgba(31, 38, 135, 0.2)',
          border: '2px solid rgba(255, 255, 255, 0.8)',
          backdropFilter: 'blur(10px)',
          paddingBottom: '80px'
        }}>
          <LineChart
            width={800}
            height={400}
            margin={{ top: 10, right: 30, left: 20, bottom: 10 }}
            style={{ margin: '0 auto' }}
          >
            <CartesianGrid strokeDasharray="3 3" stroke="rgba(158, 158, 158, 0.2)" />
            <XAxis
              dataKey="size"
              stroke="#1976D2"
              label={{
                value: 'Tamaño del Arreglo',
                position: 'insideBottom',
                offset: -10,
                fill: '#1976D2'
              }}
              type="number"
              scale="log"
              domain={['dataMin', 'dataMax']}
              tickFormatter={(value) => value.toLocaleString()}
            />
            <YAxis
              stroke="#1976D2"
              label={{
                value: 'Tiempo (ms)',
                angle: -90,
                position: 'insideLeft',
                fill: '#1976D2'
              }}
            />
            <Tooltip
              contentStyle={{
                backgroundColor: 'rgba(255, 255, 255, 0.95)',
                borderRadius: '15px',
                border: '2px solid rgba(255, 255, 255, 0.8)',
                boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
              }}
              formatter={(value) => value.toFixed(3)}
            />
            <Legend 
              verticalAlign="bottom"
              height={70}
              wrapperStyle={{
                paddingTop: '20px',
                bottom: -10,
                left: '50%',
                transform: 'translateX(-50%)',
                width: '80%',
                display: 'flex',
                justifyContent: 'center',
                flexWrap: 'wrap',
                gap: '20px',
                backgroundColor: 'rgba(255, 255, 255, 0.6)',
                borderRadius: '15px',
                padding: '10px'
              }}
            />
            
            {showData && (
              <>
                <Line
                  data={data}
                  type="monotone"
                  dataKey="insertionSort"
                  stroke="#2196F3"
                  strokeWidth={3}
                  name="Insertion Sort"
                  dot
                />
                <Line
                  data={data}
                  type="monotone"
                  dataKey="radixSort"
                  stroke="#4CAF50"
                  strokeWidth={3}
                  name="Radix Sort"
                  dot
                />
                <Line
                  data={data}
                  type="monotone"
                  dataKey="selectionSort"
                  stroke="#FFA726"
                  strokeWidth={3}
                  name="Selection Sort"
                  dot
                />
              </>
            )}
            
            {/* Líneas de tendencia */}
            {showTrendlines && (
              <>
                <Line
                  data={trendlines.insertion}
                  type="monotone"
                  dataKey="insertionSortTrend"
                  stroke="#2196F3"
                  strokeWidth={2}
                  strokeDasharray="5 5"
                  name="Insertion Sort (Tendencia)"
                  dot={false}
                />
                <Line
                  data={trendlines.radix}
                  type="monotone"
                  dataKey="radixSortTrend"
                  stroke="#4CAF50"
                  strokeWidth={2}
                  strokeDasharray="5 5"
                  name="Radix Sort (Tendencia)"
                  dot={false}
                />
                <Line
                  data={trendlines.selection}
                  type="monotone"
                  dataKey="selectionSortTrend"
                  stroke="#FFA726"
                  strokeWidth={2}
                  strokeDasharray="5 5"
                  name="Selection Sort (Tendencia)"
                  dot={false}
                />
              </>
            )}
          </LineChart>
        </div>
      </div>
    </div>
  );
}

export default App;