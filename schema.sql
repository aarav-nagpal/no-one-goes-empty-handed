-- Database Schema for KBC: No One Goes Empty Handed

CREATE DATABASE IF NOT EXISTS kbc_db;
USE kbc_db;

-- 1. questions table
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    round_number INT NOT NULL,
    question_text TEXT NOT NULL,
    opt_a VARCHAR(255) NOT NULL,
    opt_b VARCHAR(255) NOT NULL,
    opt_c VARCHAR(255) NOT NULL,
    opt_d VARCHAR(255) NOT NULL,
    correct_opt CHAR(1) NOT NULL,
    prize_amount BIGINT NOT NULL,
    INDEX idx_round (round_number)
);

-- 2. players table
CREATE TABLE IF NOT EXISTS players (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL,
    highest_round INT NOT NULL DEFAULT 0,
    prize_won BIGINT NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL,
    played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Seed data: 50 Core Java questions (5 per round for 10 rounds)
INSERT INTO questions (id, round_number, question_text, opt_a, opt_b, opt_c, opt_d, correct_opt, prize_amount) VALUES
(1, 1, 'What is the default value of a boolean instance variable in a Java class?', 'true', 'false', 'null', '0', 'B', 1000),
(2, 1, 'Which of the following is NOT a primitive data type in Java?', 'int', 'float', 'String', 'boolean', 'C', 1000),
(3, 1, 'What is the bit-width of a char data type in Java?', '8-bit', '16-bit', '32-bit', 'Depends on OS', 'B', 1000),
(4, 1, 'Which keyword is used to declare a constant variable in Java?', 'const', 'static', 'final', 'immutable', 'C', 1000),
(5, 1, 'What is the result of the integer division 9 / 2 in Java?', '4.5', '4', '5', 'Compilation Error', 'B', 1000),

(6, 2, 'Which data type is NOT permitted as a switch expression in Java 8?', 'int', 'String', 'enum', 'float', 'D', 2000),
(7, 2, 'What happens when casting a double (e.g. 9.87) to an int in Java?', 'Rounded to 10', 'Fractional part truncated to 9', 'Throws ClassCastException', 'Compilation error', 'B', 2000),
(8, 2, 'What is printed by: System.out.println(1 + 2 + "3" + 4 + 5);?', '15', '3345', '12345', '339', 'B', 2000),
(9, 2, 'Which loop structure is guaranteed to execute its block at least once?', 'for', 'while', 'do-while', 'enhanced-for', 'C', 2000),
(10, 2, 'What is the operator precedence of ++ compared to + in Java?', 'Lower', 'Higher', 'Equal', 'Evaluated right to left', 'B', 2000),

(11, 3, 'Why is String immutable in Java?', 'Security, thread-safety, and String Pool caching', 'Because char arrays cannot be modified', 'To prevent subclassing only', 'It is mutable in modern Java', 'A', 5000),
(12, 3, 'What does the == operator compare when evaluating two object references?', 'Object content equality', 'Memory reference / identity equality', 'Their hashCode values', 'Their class definitions', 'B', 5000),
(13, 3, 'What is the primary difference between StringBuffer and StringBuilder?', 'StringBuffer is thread-safe (synchronized); StringBuilder is not', 'StringBuilder is thread-safe; StringBuffer is not', 'StringBuffer is immutable; StringBuilder is mutable', 'There is no difference', 'A', 5000),
(14, 3, 'What is the return type of Object.hashCode() in Java?', 'long', 'int', 'short', 'String', 'B', 5000),
(15, 3, 'If two objects are equal according to equals(Object), what MUST be true of their hashCodes?', 'Their hashCodes must be different', 'Their hashCodes must be equal', 'Their hashCodes must be 0', 'No requirement exists', 'B', 5000),

(16, 4, 'In Java 8+, at what bin count does a HashMap bucket treeify into a Red-Black Tree (TREEIFY_THRESHOLD)?', '4', '8', '16', '32', 'B', 10000),
(17, 4, 'What is the default initial capacity of an ArrayList when created with new ArrayList<>()?', '0 until first element added, then 10', '16 immediately', '32 immediately', '8 immediately', 'A', 10000),
(18, 4, 'Which underlying collection backs a standard java.util.HashSet?', 'TreeMap', 'HashMap', 'LinkedList', 'Array', 'B', 10000),
(19, 4, 'What range of integer values is cached by default in Integer.valueOf() (IntegerCache)?', '0 to 255', '-128 to 127', '-32768 to 32767', 'No caching occurs', 'B', 10000),
(20, 4, 'What is the growth factor formula for an ArrayList when it runs out of capacity in OpenJDK?', 'oldCapacity * 2', 'oldCapacity + (oldCapacity >> 1)', 'oldCapacity + 10', 'oldCapacity * 3', 'B', 10000),

(21, 5, 'In which Java release was PermGen completely removed in favor of Metaspace?', 'Java 6', 'Java 7', 'Java 8', 'Java 9', 'C', 20000),
(22, 5, 'Where does Metaspace memory reside compared to the Java Heap?', 'In native OS memory outside the Java Heap', 'Inside Eden space', 'Inside Old Generation Heap', 'Inside Thread Stack', 'A', 20000),
(23, 5, 'What does the Weak Generational Hypothesis in garbage collection state?', 'Most objects survive indefinitely', 'Most allocated objects die shortly after creation', 'Generational GC requires WeakReferences', 'Heap memory cannot be segmented', 'B', 20000),
(24, 5, 'What memory area stores local variables and method execution frames?', 'Java Heap', 'Thread Stack', 'Metaspace', 'Code Cache', 'B', 20000),
(25, 5, 'What happens when System.gc() is invoked in application code?', 'Guarantees an immediate stop-the-world full GC', 'Suggests to the JVM that GC run, but execution is not guaranteed', 'Throws an UnsupportedOperationException', 'Deallocates Metaspace only', 'B', 20000),

(26, 6, 'What two guarantees does the volatile keyword provide in the Java Memory Model?', 'Mutual exclusion and atomicity of compound operations', 'Visibility of writes across threads and prevents instruction reordering', 'Locks the object monitor and sleeps', 'Prevents garbage collection of the variable', 'B', 40000),
(27, 6, 'What is the critical lock behavior difference between Thread.sleep() and Object.wait()?', 'sleep() releases locks; wait() retains locks', 'wait() releases monitor locks; sleep() retains locks', 'sleep() can only be called in synchronized blocks', 'wait() halts all JVM threads', 'B', 40000),
(28, 6, 'Which Thread.State represents a thread waiting to acquire a synchronized monitor lock?', 'WAITING', 'TIMED_WAITING', 'BLOCKED', 'TERMINATED', 'C', 40000),
(29, 6, 'Which bytecode instructions manage entering and exiting a synchronized block?', 'lock and unlock', 'monitorenter and monitorexit', 'cas_enter and cas_exit', 'push_lock and pop_lock', 'B', 40000),
(30, 6, 'Which condition is NOT one of Coffmans four preconditions for a deadlock?', 'Mutual Exclusion', 'Hold and Wait', 'Circular Wait', 'Preemption Allowed', 'D', 40000),

(31, 7, 'In ThreadPoolExecutor, when corePoolSize threads are busy, where is a new task placed first?', 'New thread up to maxPoolSize created immediately', 'Queued into the workQueue', 'Rejected immediately via RejectedExecutionHandler', 'Executed on the caller thread', 'B', 80000),
(32, 7, 'How does CountDownLatch differ from CyclicBarrier?', 'CountDownLatch can be reset; CyclicBarrier cannot', 'CyclicBarrier can be reset and reused; CountDownLatch cannot', 'CountDownLatch uses monitors; CyclicBarrier uses atomics', 'There is no functional difference', 'B', 80000),
(33, 7, 'What scheduling technique does ForkJoinPool employ when a worker thread exhausts its tasks?', 'Work-stealing (steals from tail of another workers deque)', 'Round-robin preemption', 'Thread interruption', 'Master-worker broadcast', 'A', 80000),
(34, 7, 'Why can ThreadLocal cause memory leaks in thread pools if remove() is not called?', 'ThreadLocal keys hold strong references to threads', 'ThreadLocalMap entries survive in pooled worker threads across requests', 'ThreadLocal prevents class unloading', 'ThreadLocal disables Old Gen GC', 'B', 80000),
(35, 7, 'Which lock in java.util.concurrent supports optimistic reads that can be validated without acquiring a read lock?', 'ReentrantLock', 'StampedLock', 'Semaphore', 'CountDownLatch', 'B', 80000),

(36, 8, 'Which JVM bytecode instruction is used to dispatch standard public/protected instance methods dynamically?', 'invokestatic', 'invokespecial', 'invokevirtual', 'invokedynamic', 'C', 160000),
(37, 8, 'Which bytecode instruction calls private instance methods and superclass constructors (<init>)?', 'invokevirtual', 'invokespecial', 'invokeinterface', 'invokestatic', 'B', 160000),
(38, 8, 'Which sub-phase of Class Linking allocates memory for class variables and initializes them to default values?', 'Verification', 'Preparation', 'Resolution', 'Initialization (<clinit>)', 'B', 160000),
(39, 8, 'What does the Bootstrap ClassLoader in OpenJDK load?', 'Application classpath dependencies', 'JDK internal core runtime classes (java.base)', 'Extension jars in /lib/ext', 'Dynamic proxy classes only', 'B', 160000),
(40, 8, 'Where are string literals, class names, method descriptors, and constants stored in a compiled .class file?', 'Execution Stack', 'Constant Pool', 'Code Section', 'LineNumberTable', 'B', 160000),

(41, 9, 'Which hardware memory barrier prevents load operations from being reordered with preceding store operations?', 'LoadLoad', 'StoreStore', 'StoreLoad', 'LoadStore', 'C', 320000),
(42, 9, 'What CPU phenomenon occurs when multiple threads modify distinct variables on the same 64-byte cache line?', 'Lock Starvation', 'False Sharing', 'Cache Miss Thrashing', 'Divergent Branching', 'B', 320000),
(43, 9, 'What annotation was introduced in Java 8 to isolate variables and eliminate False Sharing via cache-line padding?', '@VolatilePadding', '@Contended', '@ImmutableCache', '@ThreadIsolated', 'B', 320000),
(44, 9, 'What JIT C2 optimization replaces an object with its individual primitive fields in registers/stack when it does not escape?', 'Inlining', 'Scalar Replacement', 'Loop Unrolling', 'Branch Elimination', 'B', 320000),
(45, 9, 'In the JMM, a write to a volatile variable happens-before which subsequent action?', 'Any write to any variable', 'Every subsequent read of that same volatile variable', 'The next garbage collection cycle', 'The thread termination', 'B', 320000),

(46, 10, 'In Java 8+, how does ConcurrentHashMap synchronize concurrent bucket writes to the table?', 'A global ReentrantLock on all buckets', 'CAS on empty head nodes, synchronized locking on non-empty bucket head nodes', 'Segment array locks (16 segments)', 'ReadWriteLock per bucket', 'B', 70000000),
(47, 10, 'In HotSpot memory architecture, what is the distinction between OOPs and Klass structures?', 'OOP is the Java heap object representation; Klass is metadata in Metaspace', 'OOP is compiled native machine code; Klass is Java bytecode', 'OOP is the thread execution frame; Klass is the JIT profile', 'OOP is off-heap buffer; Klass is garbage collector root', 'A', 70000000),
(48, 10, 'How does sun.misc.Unsafe / VarHandle compareAndSet achieve lock-free atomicity at the x86 assembly level?', 'Via kernel sys_mutex calls', 'Via the CMPXCHG instruction with the LOCK bus prefix', 'By suspending thread execution', 'Using hardware transactional memory (RTM) only', 'B', 70000000),
(49, 10, 'What is a JVM Safepoint in HotSpot runtime execution?', 'A safe file checkpoint on disk', 'A point where all application threads stop so the runtime can safely perform GC or deoptimization', 'A state where locks are permanently disabled', 'A checkpoint where JIT compilation is saved', 'B', 70000000),
(50, 10, 'In invokedynamic (JSR 292), what method is called on the first execution to link the CallSite?', 'Reflection invoke()', 'Bootstrap Method (BSM) returning a CallSite', 'Native C++ vtable lookup', 'ClassLoader.findClass()', 'B', 70000000)
ON DUPLICATE KEY UPDATE question_text = VALUES(question_text);
