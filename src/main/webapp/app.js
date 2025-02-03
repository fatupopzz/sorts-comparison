import React from 'react';
import ReactDOM from 'react-dom';
import { Line } from 'react-chartjs-2';

const testData = {
    labels: ['Page A', 'Page B', 'Page C', 'Page D', 'Page E', 'Page F', 'Page G'],
    datasets: [
        {
            label: 'PV',
            data: [2400, 1398, 9800, 3908, 4800, 3800, 4300],
            borderColor: '#8884d8',
            fill: false,
        },
        {
            label: 'UV',
            data: [4000, 3000, 2000, 2780, 1890, 2390, 3490],
            borderColor: '#82ca9d',
            fill: false,
        },
    ],
};

const options = {
    scales: {
        x: {
            title: {
                display: true,
                text: 'Pages',
            },
        },
        y: {
            title: {
                display: true,
                text: 'Values',
            },
        },
    },
};

const App = () => {
    return (
        <div style={{ 
            background: 'white', 
            padding: '20px',
            borderRadius: '10px' 
        }}>
            <h1>Sorting Performance</h1>
            <p>If you can see this, React is working!</p>
            <div className="chart-container" style={{ padding: '20px', border: '1px solid #ccc', borderRadius: '10px', background: '#fff' }}>
                <Line data={testData} options={options} />
            </div>
        </div>
    );
};

ReactDOM.render(<App />, document.getElementById('root'));