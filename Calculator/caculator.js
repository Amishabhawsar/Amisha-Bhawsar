const canvas = document.getElementById('calculator');
const ctx = canvas.getContext('2d');
const buttonWidth = 80;
const buttonHeight = 80;
const resultX = 400; // X position for the result display
const expressionX = 0; // X position for the expression
const btnSpacing = 1;

let expression = '';
let result = '';
const fontSize = '24px Arial';

canvas.addEventListener('click', handleClick);

// Function to draw the colored circles
function drawCircles() {
    // Red circle
    ctx.fillStyle = 'red';
    ctx.beginPath();
    ctx.arc(20, 20, 10, 0, 2 * Math.PI);
    ctx.fill();

    // Yellow circle
    ctx.fillStyle = 'orange';
    ctx.beginPath();
    ctx.arc(50, 20, 10, 0, 2 * Math.PI);
    ctx.fill();

    // Green circle
    ctx.fillStyle = 'green';
    ctx.beginPath();
    ctx.arc(80, 20, 10, 0, 2 * Math.PI);
    ctx.fill();
}

function drawButton(button) {
    if(button.text == '' || button.text == '%' ){
    ctx.fillStyle = '#444'; // Background color for specific buttons
    ctx.fillRect(button.x, button.y, button.width, button.height);
    } else {
        ctx.fillStyle = 'grey'; // Background color for all other buttons
        ctx.fillRect(button.x, button.y, button.width, button.height);
    }
    

    if(button.text === 'Back'){
        ctx.strokeStyle = '#333'; // Border color for non-empty buttons in the last row
        ctx.lineWidth = 2; // Border width
        ctx.strokeRect(button.x, button.y, button.width, button.height);
        ctx.fillStyle = 'white'; // Text color
        ctx.font = "bold 24px Arial";
        ctx.fillText(button.text, button.x + button.width / 2 - 30, button.y + button.height / 2 + 10);
    }

    // Check if the button is in the last column and set its color to orange
    else if (button.x === 4 * buttonWidth) {
        ctx.fillStyle = 'orange';
        ctx.fillRect(button.x, button.y, button.width, button.height);
        ctx.strokeStyle = '#333';
        ctx.lineWidth = 2;
        ctx.strokeRect(button.x, button.y, button.width, button.height);
        ctx.fillStyle = 'white';
        ctx.fillText(button.text, button.x + button.width / 2 - 8, button.y + button.height / 2 + 10);
    }
    else {
    ctx.strokeStyle = '#333'; // Border color for non-empty buttons in the last row
    ctx.lineWidth = 2; // Border width
    ctx.strokeRect(button.x, button.y, button.width, button.height);
    ctx.fillStyle = 'white'; // Text color
    ctx.font = "bold 24px Arial";
    ctx.fillText(button.text, button.x + button.width / 2 - 8, button.y + button.height / 2 + 10);
    }
}

function drawDisplays() {
    // Input Display
    ctx.fillStyle = '#333';
    ctx.fillRect(0, 40, 400, 50);
    ctx.fillStyle = 'white';
    ctx.textAlign = "right"
    ctx.font = fontSize;
    ctx.fillText(expression, 390, 80);

    // Result Display
    ctx.fillStyle = '#333';
    ctx.fillRect(50, 120, 400, 50);
    ctx.font = "bold 32px Arial";
    ctx.fillStyle = 'white';
    ctx.textAlign = "right"
    ctx.fillText(result, 390,160);
}

function drawButtons() {
    buttons.forEach(drawButton);
}

function handleClick(event) {
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const button = buttons.find(b => x >= b.x && x <= b.x + b.width && y >= b.y && y <= b.y + b.height);

    if (button) {
        handleButtonClick(button.text);
        drawDisplays();
    }
}

const buttons = [
    // Row 1
    { text: '', x: 0, y: 180, width: buttonWidth, height: buttonHeight },
    { text: '', x: buttonWidth, y: 180, width: buttonWidth, height: buttonHeight },
    { text: '', x: 2 * buttonWidth, y: 180, width: buttonWidth, height: buttonHeight },
    { text: '%', x: 3 * buttonWidth, y: 180, width: buttonWidth, height: buttonHeight },
    { text: '/', x: 4 * buttonWidth, y: 180, width: buttonWidth, height: buttonHeight },

    // Row 2
    { text: '(', x: 0, y: 260, width: buttonWidth, height: buttonHeight },
    { text: '7', x: buttonWidth, y:260, width: buttonWidth, height: buttonHeight },
    { text: '8', x: 2 * buttonWidth, y: 260, width: buttonWidth, height: buttonHeight },
    { text: '9', x: 3 * buttonWidth, y: 260, width: buttonWidth, height: buttonHeight },
    { text: '*', x: 4 * buttonWidth, y: 260, width: buttonWidth, height: buttonHeight },

    // Row 3
    { text: ')', x: 0, y: 340, width: buttonWidth, height: buttonHeight },
    { text: '4', x: buttonWidth, y: 340, width: buttonWidth, height: buttonHeight },
    { text: '5', x: 2 * buttonWidth, y: 340, width: buttonWidth, height: buttonHeight },
    { text: '6', x: 3 * buttonWidth, y: 340, width: buttonWidth, height: buttonHeight },
    { text: '-', x: 4 * buttonWidth, y: 340, width: buttonWidth, height: buttonHeight },

    // Row 4
    { text: 'Back', x: 0, y: 420, width: buttonWidth, height: buttonHeight },
    { text: '1', x: buttonWidth, y: 420, width: buttonWidth, height: buttonHeight },
    { text: '2', x: 2 * buttonWidth, y: 420, width: buttonWidth, height: buttonHeight },
    { text: '3', x: 3 * buttonWidth, y: 420, width: buttonWidth, height: buttonHeight },
    { text: '+', x: 4 * buttonWidth, y: 420, width: buttonWidth, height: buttonHeight },

    // Row 5
    { text: '0', x: 0, y: 500, width: 3 * buttonWidth, height: buttonHeight }, // Combine three buttons into one
    { text: '.', x: 3 * buttonWidth, y: 500, width: buttonWidth, height: buttonHeight },
    { text: '=', x: 4 * buttonWidth, y: 500, width: buttonWidth, height: buttonHeight },
];

function handleButtonClick(text) {
    if (text === '=') {
        evaluateExpression();
    } else if (text === 'Back') {
        backspace();
    } else {
        appendToExpression(text);
    }
}

function backspace() {
    expression = expression.slice(0, -1);
}

function appendToExpression(value) {
    if (expression === 'Invalid Expression') {
        expression = '';
    }
    expression += value;
}

function evaluateExpression() {
    result = parseExpression(expression);
}

// Custom expression parser
function parseExpression(input) {
    const operators = ['+', '-', '*', '/','%','.'];
    // const tokens = input.match(/\d+|[\+\-\*\/]/g);
    const tokens = input.match(/\d+(\.\d+)?|[\+\-\*\/\(\)%]/g);

    if (!tokens) {
        return 'Invalid Expression';
    }

    let stack = [];
    let output = [];
    for (let token of tokens) {
        if (!isNaN(token)) {
            output.push(parseFloat(token));
        } else if (operators.includes(token)) {
            while (stack.length && operators.includes(stack[stack.length - 1])) {
                let op1 = token;
                let op2 = stack[stack.length - 1];
                if (operators.indexOf(op1) > operators.indexOf(op2)) {
                    break;
                }
                output.push(stack.pop());
            }
            stack.push(token);
        } else if (token === '(') {
            stack.push(token);
        } else if (token === ')') {
            while (stack.length && stack[stack.length - 1] !== '(') {
                output.push(stack.pop());
            }
            if (stack[stack.length - 1] === '(') {
                stack.pop();
            } else {
                return 'Invalid Expression';
            }
        }
    }
    while (stack.length) {
        if (stack[stack.length - 1] === '(' || stack[stack.length - 1] === ')') {
            return 'Invalid Expression';
        }
        output.push(stack.pop());
    }

    stack = [];
    for (let token of output) {
        if (!isNaN(token)) {
            stack.push(token);
        } else if (operators.includes(token)) {
            if (stack.length < 2) {
                return 'Invalid Expression';
            }
            let b = stack.pop();
            let a = stack.pop();
            switch (token) {
                case '+':
                    stack.push(a + b);
                    break;
                case '-':
                    stack.push(a - b);
                    break;
                case '*':
                    stack.push(a * b);
                    break;
                case '/':
                    if (b === 0) {
                        return 'Invalid Expression';
                    }
                    stack.push(a / b);
                    break;
                case '%':
                    stack.push(a % b);
                    break;
            }
        }
    }
    if (stack.length !== 1) {
        return 'Invalid Expression';
    }
    return stack[0];
}

function drawCalculator() {
    clearCanvas();
    drawButtons();
    drawDisplays();
    drawCircles();
}

function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

drawCalculator();
