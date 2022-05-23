# Maze Gen
This is a simple maze generator and solver which I wrote for my data structures and algorithmic analysis class. A maze is randomly generated using Prim's algorithm and printed to the terminal with line-drawing characters. By pressing `Enter`, the path through the maze may be shown.
```
Enter the dimensions of the maze, e.g. '16x24': 8x8
╷   ╶───┬───┬───────────┬───────┐
│       │   │           │       │
│   ┌───┤   ├───┐   ╶───┘   ╶───┤
│   │   │   │   │               │
│   ╵   │   ╵   │   ╶───────────┤
│       │       │               │
│   ╶───┘   ╶───┤   ╶───┐   ╶───┤
│               │       │       │
│   ╶───┐   ╷   ╵   ┌───┴───┐   │
│       │   │       │       │   │
│   ╶───┼───┴───┐   ╵   ╶───┼───┤
│       │       │           │   │
├───╴   ╵   ╶───┴───┬───────┤   │
│                   │       │   │
├───────╴   ╶───────┘   ╶───┘   │
│                               │
└───────────────────────────╴   ╵

Give up? Press 'Enter' to show the maze solution.

Solved maze with path: SSSSSESESEEEEE

╷▐█▌╶───┬───┬───────────┬───────┐
│▐█▌    │   │           │       │
│▐█▌┌───┤   ├───┐   ╶───┘   ╶───┤
│▐█▌│   │   │   │               │
│▐█▌╵   │   ╵   │   ╶───────────┤
│▐█▌    │       │               │
│▐█▌╶───┘   ╶───┤   ╶───┐   ╶───┤
│▐█▌            │       │       │
│▐█▌╶───┐   ╷   ╵   ┌───┴───┐   │
│▐█▌    │   │       │       │   │
│▐█▌╶───┼───┴───┐   ╵   ╶───┼───┤
│▐█████▌│       │           │   │
├───╴▐█▌╵   ╶───┴───┬───────┤   │
│    ▐█████▌        │       │   │
├───────╴▐█▌╶───────┘   ╶───┘   │
│        ▐█████████████████████▌│
└───────────────────────────╴▐█▌╵
```
