
# Create a new PowerPoint presentation
ppt = Presentation() # type: ignore

# Define a standard slide layout
title_slide_layout = ppt.slide_layouts[0]  # Title Slide
content_slide_layout = ppt.slide_layouts[1]  # Content Slide

# Slide 1: Title Slide
slide = ppt.slides.add_slide(title_slide_layout)
title = slide.shapes.title
subtitle = slide.placeholders[1]
title.text = "Alt Tag Automation Web - Proof of Concept (PoC)"
subtitle.text = "Enhancing Efficiency and Accuracy in Alt Tag Validation\nDate: January 17, 2025\nClient: Genentech"

# Slide 2: Objective
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Objective"
content.text = (
    "Purpose:\n"
    "• Demonstrate the automation of alt tag validation, transitioning from manual efforts to an efficient, accurate automated process.\n\n"
    "Goals:\n"
    "• Eliminate redundant tasks.\n"
    "• Simplify input preparation.\n"
    "• Remove dependency on third-party sorting scripts."
)

# Slide 3: Scope
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Scope"
content.text = (
    "In-Scope:\n"
    "• Automating alt tag validation for UAT, Prod, and Sanity environments.\n"
    "• Validating alt tags/images and generating user-friendly output for bug reporting.\n\n"
    "Out-of-Scope:\n"
    "• Handling non-alt tag validations.\n"
    "• Multithreading functionality (planned for future enhancements)."
)

# Slide 4: Technical Overview
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Technical Overview"
content.text = (
    "Programming Language: Java\n"
    "Frameworks: Selenium, TestNG\n"
    "Build Tool: Maven\n\n"
    "Highlights:\n"
    "• Efficient alt tag validation.\n"
    "• Direct generation of sorted output files.\n"
    "• No preprocessing or third-party sorting scripts required."
)

# Slide 5: Implementation Details
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Implementation Details"
content.text = (
    "Code Structure:\n"
    "• Modular approach with clear logic separation for input handling, validation, and output generation.\n"
    "• Optimized to remove reliance on macros or third-party scripts for output sorting.\n\n"
    "Execution Flow:\n"
    "1. Input provided in a straightforward format.\n"
    "2. Script navigates URLs, validates alt tags/images against criteria.\n"
    "3. Output file generated with clear, actionable results."
)

# Slide 6: Demo Scenarios
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Demo Scenarios"
content.text = (
    "Scenarios to Demonstrate:\n"
    "1. Alt Fail: Images with missing/incorrect alt tags.\n"
    "2. Img Pass: Images meeting validation criteria.\n"
    "3. Output Generation: Automated creation of a sorted, user-friendly file highlighting detected issues.\n\n"
    "Reference: Details available in the SCENARIO DOC."
)

# Slide 7: Challenges Addressed
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Challenges Addressed"
content.text = (
    "• Increased script efficiency by 50-60% compared to the manual process.\n"
    "• Removed third-party script dependency for data sorting.\n"
    "• Simplified input data preparation, reducing user effort."
)

# Slide 8: Benefits
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Benefits"
content.text = (
    "Efficiency:\n"
    "• Significantly faster execution with optimized performance.\n\n"
    "Simplicity:\n"
    "• No need for external tools to process outputs.\n\n"
    "Usability:\n"
    "• Ready-to-use output files for direct bug reporting."
)

# Slide 9: Next Steps
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Next Steps"
content.text = (
    "Planned Enhancements:\n"
    "• Implement multithreading to improve performance by processing multiple URLs simultaneously.\n\n"
    "Feedback Integration:\n"
    "• Address feedback from the POC presentation to refine functionality.\n\n"
    "Bug Fixes & Improvements:\n"
    "• Resolve any identified issues and enhance performance further."
)

# Slide 10: Thank You
slide = ppt.slides.add_slide(content_slide_layout)
title = slide.shapes.title
content = slide.placeholders[1]
title.text = "Thank You"
content.text = "Thank you for your time and attention!\n\nContact us for further details."

# Save the presentation to a file
file_path = "/mnt/data/Alt_Tag_Automation_PoC.pptx"
ppt.save(file_path)

file_path
