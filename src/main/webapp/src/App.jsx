import { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

function App() {
  const [data, setData] = useState([]);
  const [regressions, setRegressions] = useState({
    insertion: [],
    merge: [],
    quick: [],
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
        
        // Transformar datos crudos
        const rawData = jsonData.rawData.map(item => ({
          size: item.size,
          insertionSort: item.insertionTime / 1000000,
          mergeSort: item.mergeTime / 1000000,
          quickSort: item.quickTime / 1000000,
          radixSort: item.radixTime / 1000000,
          selectionSort: item.selectionTime / 1000000
        }));

        setData(rawData);

        // Preparar datos de regresión
        setRegressions({
          insertion: jsonData.regressions.insertion.map(p => ({
            size: p.x,
            insertionSortTrend: p.y
          })),
          merge: jsonData.regressions.merge.map(p => ({
            size: p.x,
            mergeSortTrend: p.y
          })),
          quick: jsonData.regressions.quick.map(p => ({
            size: p.x,
            quickSortTrend: p.y
          })),
          radix: jsonData.regressions.radix.map(p => ({
            size: p.x,
            radixSortTrend: p.y
          })),
          selection: jsonData.regressions.selection.map(p => ({
            size: p.x,
            selectionSortTrend: p.y
          }))
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
            height={500}
            margin={{ top: 20, right: 30, left: 20, bottom: 60 }}
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
                  dataKey="mergeSort"
                  stroke="#9C27B0"
                  strokeWidth={3}
                  name="Merge Sort"
                  dot
                />
                <Line
                  data={data}
                  type="monotone"
                  dataKey="quickSort"
                  stroke="#E91E63"
                  strokeWidth={3}
                  name="Quick Sort"
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
            
            {showTrendlines && (
              <>
                <Line
                  data={regressions.insertion}
                  type="monotone"
                  dataKey="insertionSortTrend"
                  stroke="#2196F3"
                  strokeWidth={2}
                  strokeDasharray="5 5"
                  name="Insertion Sort (Tendencia)"
                  dot={false}
                />
                <Line
                  data={regressions.merge}
                  type="monotone"
                  dataKey="mergeSortTrend"
                  stroke="#9C27B0"
                  strokeWidth={2}
                  strokeDasharray="5 5"
                  name="Merge Sort (Tendencia)"
                  dot={false}
                />
                <Line
                  data={regressions.quick}
                  type="monotone"
                  dataKey="quickSortTrend"
                  stroke="#E91E63"
                  strokeWidth={2}
                  strokeDasharray="5 5"
                  name="Quick Sort (Tendencia)"
                  dot={false}
                />
                <Line
                  data={regressions.radix}
                  type="monotone"
                  dataKey="radixSortTrend"
                  stroke="#4CAF50"
                  strokeWidth={2}
                  strokeDasharray="5 5"
                  name="Radix Sort (Tendencia)"
                  dot={false}
                />
                <Line
                  data={regressions.selection}
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