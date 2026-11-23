package com.kbc.database;

import com.kbc.models.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Built-in bank of 50 Core Java questions (5 per round for 10 rounds)
 * strictly adhering to the 10-round difficulty curve and MCQ format:
 * - 1 obviously wrong option
 * - 2 very similar/confusing options (one is correct)
 * - 1 option slightly longer than the rest (bait or correct)
 */
public class DefaultQuestionBank {

    private static final List<Question> QUESTIONS = new ArrayList<>();

    static {
        // ====================================================================================================
        // ROUND 1 (₹1,000): Easy Syntax & Primitives
        // ====================================================================================================
        add(1, 1, "What is the default value of a boolean instance variable in a Java class?",
                "false",
                "true",
                "definitely_false_unless_otherwise_specified_by_the_developer_at_runtime",
                "42",
                "A", 1000L);

        add(2, 1, "Which of the following is a primitive data type in Java?",
                "String",
                "def_func",
                "Integer",
                "byte representing an 8-bit signed two's complement integer",
                "D", 1000L);

        add(3, 1, "What is the size of an int primitive data type in Java?",
                "32 bits across all operating systems and platforms",
                "16 bits",
                "32 bytes",
                "1 kilogram",
                "A", 1000L);

        add(4, 1, "Which keyword is used to prevent a class from being subclassed in Java?",
                "static",
                "final",
                "const",
                "sealed_and_cannot_be_inherited_by_any_child_class_whatsoever",
                "B", 1000L);

        add(5, 1, "What is the result of the expression (11 % 3) in Java?",
                "print(\"remainder\")",
                "2",
                "3",
                "3.6666666666666665 calculated as floating point division",
                "B", 1000L);

        // ====================================================================================================
        // ROUND 2 (₹2,000): Control Flow & Operators
        // ====================================================================================================
        add(6, 2, "Which data type is NOT allowed in a switch statement condition prior to Java 12?",
                "float",
                "double",
                "System.out.println(\"Switch\")",
                "String objects supported only when evaluated via custom regex expressions",
                "A", 2000L);

        add(7, 2, "What is the output of System.out.println(10 + 20 + \"30\"); in Java?",
                "\"102030\"",
                "\"3030\"",
                "\"Blueberry Pancake\"",
                "\"60\" after evaluating all operands strictly as mathematical integers",
                "B", 2000L);

        add(8, 2, "Which loop construct evaluates its condition at the end of the iteration block?",
                "goto loop",
                "while loop",
                "do-while loop that is guaranteed to execute its block at least once",
                "do-until loop",
                "C", 2000L);

        add(9, 2, "What is the effect of the continue statement inside an inner loop in Java?",
                "Skips the rest of current iteration of the innermost enclosing loop",
                "Terminates the entire program immediately with status code 0",
                "Breaks out of the innermost enclosing loop entirely",
                "Breaks out of all outer enclosing loops entirely",
                "A", 2000L);

        add(10, 2, "What is the difference between prefix ++i and postfix i++ operators in Java?",
                "++i increments after expression evaluation, whereas i++ increments before",
                "++i increments before expression evaluation, whereas i++ increments after",
                "One is for Java while the other is exclusive to COBOL and Fortran",
                "Both operators are strictly identical in bytecode and behave identically in every scenario",
                "B", 2000L);

        // ====================================================================================================
        // ROUND 3 (₹5,000): Strings & Object Fundamentals
        // ====================================================================================================
        add(11, 3, "Where are literal String objects stored inside JVM memory in Java 8+?",
                "Stack frame memory",
                "String Constant Pool located inside the Java Heap memory space",
                "PermGen space",
                "Metaspace",
                "B", 5000L);

        add(12, 3, "What does the expression (s1 == s2) compare when s1 and s2 are object references?",
                "Lexicographical contents of the objects",
                "Memory addresses to determine if both references point to the exact same object",
                "Object hashCode values",
                "Whether both objects have matching variable names in source code",
                "B", 5000L);

        add(13, 3, "Which class provides a mutable sequence of characters and is safe for multi-threaded access?",
                "StringBuilder",
                "StringBuffer with synchronized methods for thread-safe concurrent modifications",
                "StringJoiner",
                "std::stringstream",
                "B", 5000L);

        add(14, 3, "What is the contract between equals() and hashCode() according to the Java specification?",
                "If equals() is true, hashCode() must return the same integer for both objects",
                "If hashCode() is equal, equals() must always return true for both objects",
                "If equals() is false, hashCode() must return different integers",
                "Objects with the same name must share a CPU hardware register",
                "A", 5000L);

        add(15, 3, "What happens when you call intern() on a String object in Java?",
                "Deletes the string from memory to free space",
                "Returns canonical representation from String Pool, adding it if not present",
                "Makes the String instance permanently mutable",
                "Makes the String instance temporarily mutable",
                "B", 5000L);

        // ====================================================================================================
        // ROUND 4 (₹10,000): Collections Internals
        // ====================================================================================================
        add(16, 4, "In Java 8+ HashMap, when does a bucket bin convert from a linked list to a red-black tree?",
                "Bin count reaches 8 and overall table capacity is at least 64",
                "Bin count reaches 8 regardless of table capacity",
                "Bin count reaches 16 and table capacity is at least 32",
                "When the programmer sends an email to James Gosling",
                "A", 10000L);

        add(17, 4, "What is the default growth formula when an ArrayList expands its capacity in OpenJDK?",
                "newCapacity = oldCapacity * 2",
                "newCapacity = oldCapacity + (oldCapacity >> 1)",
                "newCapacity = oldCapacity + 1000 elements allocated dynamically in heap",
                "It shrinks by 50%",
                "B", 10000L);

        add(18, 4, "How does java.util.HashSet ensure all stored elements are unique internally?",
                "Backing array sorted with quicksort on each insertion",
                "Backing HashMap storing elements as keys with a constant dummy Object as value",
                "Backing TreeMap storing elements as values with Integer keys",
                "Backing HashMap storing elements as values with Integer keys",
                "B", 10000L);

        add(19, 4, "What range of values is cached by default by the Java IntegerCache in Integer.valueOf()?",
                "-128 to 127 inclusive defined by the Java Language Specification",
                "-127 to 128",
                "0 to 255",
                "-999999 to +999999",
                "A", 10000L);

        add(20, 4, "What does the load factor (default 0.75) in a HashMap determine?",
                "The threshold at which table capacity is doubled: threshold = capacity * loadFactor",
                "The maximum number of collisions allowed per bucket before resizing",
                "The minimum number of collisions allowed per bucket before resizing",
                "The percentage of CPU cores dedicated to computing hash codes",
                "A", 10000L);

        // ====================================================================================================
        // ROUND 5 (₹20,000): Memory Management & Heap/Metaspace
        // ====================================================================================================
        add(21, 5, "Where was PermGen relocated starting in Java 8, and what memory does it utilize?",
                "Moved to Java Heap memory managed by the G1 Garbage Collector",
                "Moved to Metaspace which allocates from native off-heap operating system memory",
                "Moved to Thread Stack memory managed by the JVM execution engine",
                "Saved into an encrypted SQLite database on the hard drive",
                "B", 20000L);

        add(22, 5, "What is the Weak Generational Hypothesis upon which HotSpot GC algorithms are based?",
                "Older objects are garbage collected much faster than newly created objects",
                "Most allocated objects have very short lifespans and die soon after creation",
                "Younger objects are garbage collected much slower than older objects",
                "Computers get weaker each generation unless upgraded with more RGB lights",
                "B", 20000L);

        add(23, 5, "Which JVM memory area holds method local primitive variables and object reference variables?",
                "Metaspace",
                "Thread Stack containing execution frames for each actively executing thread",
                "Code Cache",
                "The GPU video RAM",
                "B", 20000L);

        add(24, 5, "What is the guaranteed effect of executing System.gc() in production Java code?",
                "Guarantees an immediate stop-the-world Full GC across all generations",
                "Merely hints to the JVM that GC may run, with no guarantee of execution",
                "Guarantees an immediate Minor GC in Eden space only",
                "Turns the computer monitor off and on again",
                "B", 20000L);

        add(25, 5, "What error is thrown when Metaspace exceeds -XX:MaxMetaspaceSize?",
                "java.lang.StackOverflowError",
                "java.lang.OutOfMemoryError: Metaspace indicating native memory exhaustion for class metadata",
                "java.lang.OutOfMemoryError: Java heap space",
                "java.lang.SegfaultKernelPanicException",
                "B", 20000L);

        // ====================================================================================================
        // ROUND 6 (₹40,000): Threads & Intrinsic Monitors
        // ====================================================================================================
        add(26, 6, "What guarantees does the volatile keyword provide for a variable in Java?",
                "Mutual exclusion locking preventing any two threads from reading simultaneously",
                "Visibility of writes to all threads and inhibition of instruction reordering",
                "Atomic compound operations such as volatile count++ increments",
                "Encrypts the variable using quantum blockchain algorithms",
                "B", 40000L);

        add(27, 6, "What is the fundamental difference regarding monitors between Thread.sleep() and Object.wait()?",
                "wait() releases the monitor lock; sleep() continues to hold the monitor lock",
                "sleep() releases the monitor lock; wait() continues to hold the monitor lock",
                "Both methods release all monitor locks unconditionally",
                "sleep() can only be called from inside a catch block",
                "A", 40000L);

        add(28, 6, "Which Thread.State corresponds to a thread blocked waiting to enter a synchronized block?",
                "WAITING",
                "BLOCKED waiting for a monitor lock to be released by another thread",
                "TIMED_WAITING",
                "SLEEPING_BEAUTY",
                "B", 40000L);

        add(29, 6, "Which JVM bytecode instructions are generated for a synchronized(obj) block?",
                "thread_lock and thread_unlock",
                "monitorenter and monitorexit paired with an exception handler to ensure release",
                "monitor_acquire and monitor_release",
                "sudo_lock and sudo_unlock",
                "B", 40000L);

        add(30, 6, "Which of the following is NOT one of Coffman's four deadlock preconditions?",
                "Mutual Exclusion",
                "Preemption Allowed enabling the OS to forcibly revoke held locks",
                "Circular Wait",
                "Spontaneous Combustion of the motherboard",
                "B", 40000L);

        // ====================================================================================================
        // ROUND 7 (₹80,000): Advanced Concurrency & ThreadPools
        // ====================================================================================================
        add(31, 7, "In ThreadPoolExecutor with an unbounded queue, what happens when all corePoolSize threads are busy?",
                "Additional worker threads are spawned immediately up to maximumPoolSize",
                "New incoming tasks are placed into the workQueue and maximumPoolSize is ignored",
                "Tasks are immediately rejected via RejectedExecutionException",
                "The JVM halts execution until the CPU cools down",
                "B", 80000L);

        add(32, 7, "How does CyclicBarrier differ functionally from CountDownLatch in java.util.concurrent?",
                "CountDownLatch can be reset to its initial count; CyclicBarrier cannot",
                "CyclicBarrier can be reset and reused across multiple cycles; CountDownLatch cannot",
                "CountDownLatch uses hardware CAS; CyclicBarrier uses OS kernel semaphores",
                "CyclicBarrier is written in assembly while CountDownLatch is pure Python",
                "B", 80000L);

        add(33, 7, "How does a worker thread in ForkJoinPool steal work from another worker thread?",
                "Pops tasks from the opposite end (tail) of the victim thread's double-ended queue",
                "Pushes tasks into the head of the victim thread's priority queue",
                "Pops tasks from the same end (head) of the victim thread's double-ended queue",
                "Sends a polite HTTP request over port 8080",
                "A", 80000L);

        add(34, 7, "Why can ThreadLocal variables cause memory leaks in thread-pooled web servers (like Tomcat)?",
                "ThreadLocal keys are held via soft references that prevent garbage collection",
                "Worker threads outlive request cycles and retain ThreadLocal values if remove() is omitted",
                "ThreadLocal keys are held via weak references that delete the thread instantly",
                "ThreadLocal consumes all battery power on the client device",
                "B", 80000L);

        add(35, 7, "What feature distinguishes StampedLock over ReentrantReadWriteLock in Java 8+?",
                "Optimistic read mode validating stamped tokens without acquiring actual read locks",
                "Pessimistic read mode requiring exclusive hardware bus locking",
                "Optimistic write mode allowing concurrent writers without synchronization",
                "Requires physical postage stamps attached to the server chassis",
                "A", 80000L);

        // ====================================================================================================
        // ROUND 8 (₹1,60,000): JVM Bytecode & Class Loading
        // ====================================================================================================
        add(36, 8, "Which bytecode instruction is used to dispatch standard public instance method calls dynamically?",
                "invokespecial",
                "invokevirtual which utilizes a virtual method table (vtable) to resolve calls",
                "invokestatic",
                "telepathy_invoke",
                "B", 160000L);

        add(37, 8, "Which bytecode instruction calls private instance methods and superclass constructors (<init>)?",
                "invokevirtual",
                "invokespecial which performs non-virtual static binding based on reference type",
                "invokeinterface",
                "dial_number",
                "B", 160000L);

        add(38, 8, "In Class Linking, what occurs specifically during the Preparation phase?",
                "Executes static initializers (<clinit>) and assigns user-defined initial values",
                "Allocates memory for static fields and initializes them to their type default values",
                "Replaces symbolic references in the constant pool with direct memory references",
                "Cooks a cup of coffee for the JVM garbage collector",
                "B", 160000L);

        add(39, 8, "What classes does the Bootstrap ClassLoader in HotSpot JVM load?",
                "Application classes found in the user CLASSPATH environment variable",
                "Core Java platform runtime classes in the java.base module from the runtime image",
                "Classes located in ext directory and dynamic runtime proxy classes",
                "JavaScript files downloaded from npm",
                "B", 160000L);

        add(40, 8, "What role does the Constant Pool play inside a compiled .class file?",
                "Acts as a symbol table holding literals, field/method references, and type descriptors",
                "Stores thread stack execution frames for all actively running methods",
                "Holds compiled JIT machine code ready for direct CPU execution",
                "A swimming pool where HotSpot engineers relax in summer",
                "A", 160000L);

        // ====================================================================================================
        // ROUND 9 (₹3,20,000): Java Memory Model & Low-Level Mechanics
        // ====================================================================================================
        add(41, 9, "Which CPU memory barrier prevents subsequent loads from being reordered with preceding stores?",
                "LoadLoad",
                "StoreLoad which is the most heavyweight barrier, often mapping to mfence on x86",
                "StoreStore",
                "FirewallBarrier",
                "B", 320000L);

        add(42, 9, "What is False Sharing in multi-threaded concurrent programming?",
                "Threads sharing the same logical heap object resulting in heap corruption",
                "Independent variables modified by different threads residing on the same 64-byte CPU cache line",
                "Threads sharing the same OS thread handle causing kernel deadlock",
                "Sharing Netflix passwords with friends outside your household",
                "B", 320000L);

        add(43, 9, "Which annotation was introduced in Java 8 to prevent False Sharing via cache-line padding?",
                "@ThreadLocal",
                "@jdk.internal.vm.annotation.Contended which pads fields to isolate CPU cache lines",
                "@VolatilePadding",
                "@PleaseDoNotShareMyCache",
                "B", 320000L);

        add(44, 9, "What optimization does the C2 JIT compiler perform when Escape Analysis proves an object does not escape?",
                "Scalar Replacement decomposing the object into primitive local variables on registers/stack",
                "Promotes the object directly into Old Generation Tenured heap space",
                "Serializes the object directly to disk to conserve memory",
                "Deletes the entire class definition from Metaspace",
                "A", 320000L);

        add(45, 9, "According to the JMM, which action establishes a happens-before edge with an unlock on a monitor?",
                "Any subsequent read of any volatile variable in another thread",
                "A subsequent lock on that exact same monitor by any thread",
                "A subsequent lock on any arbitrary monitor across the entire JVM runtime instance",
                "Restarting the computer power supply",
                "B", 320000L);

        // ====================================================================================================
        // ROUND 10 (₹7,00,00,000): Advanced HotSpot & Concurrency Internals
        // ====================================================================================================
        add(46, 10, "How does Java 8+ ConcurrentHashMap synchronize writes to individual buckets?",
                "Uses CAS for empty bucket heads and synchronizes on the first node for non-empty buckets",
                "Uses synchronized locks on empty heads and CAS on non-empty bucket nodes",
                "Locks the entire table using a global ReentrantReadWriteLock",
                "Flips a coin and hopes for the best",
                "A", 70000000L);

        add(47, 10, "In HotSpot internal VM architecture, what is the key distinction between an OOP and a Klass?",
                "OOP represents instance data in the Heap; Klass represents metadata and method vtables in Metaspace",
                "OOP represents metadata in Metaspace; Klass represents instance data in the Heap",
                "OOP is bytecode in class files; Klass is compiled native machine code",
                "OOP is Object Oriented Programming; Klass is a typo of Class",
                "A", 70000000L);

        add(48, 10, "How does sun.misc.Unsafe / VarHandle compareAndSet (CAS) achieve hardware atomicity on x86 CPUs?",
                "Executes the CMPXCHG assembly instruction prefixed with the LOCK bus signal",
                "Executes the XCHG instruction without any bus locking mechanism",
                "Suspends all kernel threads via operating system hardware interrupts",
                "Sends a Morse code signal to the motherboard BIOS",
                "A", 70000000L);

        add(49, 10, "What occurs when the HotSpot JVM runtime brings threads to a Safepoint?",
                "Application mutator threads pause at polling checkpoints so GC or deoptimization can run safely",
                "Only daemon threads pause while application threads continue executing concurrently",
                "Only compiler threads pause while application mutator threads continue running",
                "The antivirus runs a full system scan",
                "A", 70000000L);

        add(50, 10, "In an invokedynamic instruction, how is the CallSite linked during its bootstrap resolution?",
                "The JVM executes a Bootstrap Method (BSM) that returns a CallSite linked to a MethodHandle",
                "The JVM performs dynamic reflection invoke() on java.lang.reflect.Method directly",
                "The JVM compiles a C++ native stub that calls the target class vtable directly",
                "By summoning the ghost of Alan Turing",
                "A", 70000000L);
    }

    private static void add(int id, int round, String text, String optA, String optB, String optC, String optD, String correct, long prize) {
        Question q = Question.builder()
                .id(id)
                .roundNumber(round)
                .questionText(text)
                .optA(optA)
                .optB(optB)
                .optC(optC)
                .optD(optD)
                .correctOpt(correct)
                .prizeAmount(prize)
                .build();
        QUESTIONS.add(q);
    }

    public static List<Question> getAllQuestions() {
        return Collections.unmodifiableList(QUESTIONS);
    }

    public static Question getRandomQuestionByRound(int round, int excludeQuestionId) {
        List<Question> matching = new ArrayList<>();
        for (Question q : QUESTIONS) {
            if (q.getRoundNumber() == round) {
                if (excludeQuestionId <= 0 || q.getId() != excludeQuestionId) {
                    matching.add(q);
                }
            }
        }
        if (matching.isEmpty()) {
            for (Question q : QUESTIONS) {
                if (q.getRoundNumber() == round) {
                    matching.add(q);
                }
            }
        }
        if (matching.isEmpty()) {
            return null;
        }
        Collections.shuffle(matching);
        return matching.get(0);
    }
}
